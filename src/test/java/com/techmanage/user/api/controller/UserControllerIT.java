package com.techmanage.user.api.controller;

import com.techmanage.user.domain.model.User;
import com.techmanage.user.domain.model.UserType;
import com.techmanage.user.domain.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private UserRepository userRepository;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private User user;
    private final String MSG_EMAIL_ALREADY_IN_USE = "Email is already in use. Email: %s";
    private final String MSG_USER_NOT_FOUND = "User not found with id %d";
    
    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/api/users";

        user = new User("John Doe", "johndoe@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getAllUser_ShouldReturnAllUsers() {
        var usersCount = (int) userRepository.count();
        given()
                .accept(ContentType.JSON)
                .when()
                .get()
                .then().statusCode(HttpStatus.OK.value())
                .body("", hasSize(usersCount));
    }

    @Test
    void getUserById_ShouldReturnUser() {
        given()
                .pathParam("id", user.getId())
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then().statusCode(HttpStatus.OK.value())
                .body("name", equalTo(user.getName()))
                .body("email", equalTo(user.getEmail()))
                .body("phone", equalTo(user.getPhone()))
                .body("birthDate", equalTo(sdf.format(user.getBirthDate())))
                .body("userType", equalTo(user.getUserType().name()));
    }

    @Test
    void getUserById_ShouldReturnError_WhenUserNotFound() {
        given()
                .pathParam("id", Long.MAX_VALUE)
                .accept(ContentType.JSON)
                .when()
                .get("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo(String.format(MSG_USER_NOT_FOUND, Long.MAX_VALUE)));
    }

    @Test
    void crateUser_ShouldReturnUser() {
        var newUser = new User("John Doe", "johndoe123@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        given()
                .body(getJsonFromUser(newUser))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("name", equalTo(newUser.getName()))
                .body("email", equalTo(newUser.getEmail()))
                .body("phone", equalTo(newUser.getPhone()))
                .body("birthDate", equalTo(sdf.format(user.getBirthDate())))
                .body("userType", equalTo(newUser.getUserType().name()));
    }

    @Test
    void creatUser_ShouldReturnError_WhenInvalidFields() {
        var newUser = new User("", "", "", Date.from(Instant.now().plusSeconds(9999999999L)), UserType.ADMIN);
        given()
                .body(getJsonFromUser(newUser))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("status", equalTo(400))
                .body("error", equalTo("Invalid parameters"))
                .body("message", containsString("One or more fields are invalid"))
                .body("path", equalTo("/api/users"))
                .body("objects", hasSize(6))
                .body("objects.field", hasItems("name", "phone", "email", "birthDate"))
                .body("objects.message", hasItems(
                        "size must be between 3 and 255",
                        "invalid phone number. Use the format: +XX XX XXXXX-XXXX",
                        "must not be blank",
                        "must be a past date"
                ));
    }

    @Test
    void creatUser_ShouldReturnError_WhenEmailAlreadyInUse() {
        var newUser = new User("John Doe", "johndoe@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        given()
                .body(getJsonFromUser(newUser))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo(String.format(MSG_EMAIL_ALREADY_IN_USE, user.getEmail())));
    }

    @Test
    void deleteUser_ShouldDeleteUser() {
        given()
                .pathParam("id", user.getId())
                .accept(ContentType.JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteUser_ShouldReturnError_WhenUserDoesNotExist() {
        given()
                .pathParam("id", Long.MAX_VALUE)
                .accept(ContentType.JSON)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo(String.format(MSG_USER_NOT_FOUND, Long.MAX_VALUE)));
    }

    @Test
    void updateUser_ShouldUpdateUser() {
        var newUser = new User("John Doe", UUID.randomUUID() + "@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        given()
                .pathParam("id", user.getId())
                .body(getJsonFromUser(newUser))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(user.getId().intValue()))
                .body("name", equalTo(newUser.getName()))
                .body("phone", equalTo(newUser.getPhone()))
                .body("email", equalTo(newUser.getEmail()))
                .body("birthDate", equalTo(sdf.format(newUser.getBirthDate())))
                .body("userType", equalTo(newUser.getUserType().name()));
    }

    @Test
    void updateUser_ShouldReturnError_WhenUserDoesNotExist() {
        var newUser = new User("John Doe", UUID.randomUUID() + "@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        given()
                .pathParam("id", Long.MAX_VALUE)
                .body(getJsonFromUser(newUser))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("message", equalTo(String.format(MSG_USER_NOT_FOUND, Long.MAX_VALUE)));
    }

    @Test
    void updateUser_ShouldReturnError_WhenEmailAlreadyInUse() {
        var newUser = new User("Jane Doe", "janedoe@email.com", "+55 11 99999-9999", new Date(), UserType.ADMIN);
        userRepository.save(newUser);
        newUser.setEmail(user.getEmail());

        given()
                .pathParam("id", newUser.getId())
                .body(getJsonFromUser(newUser))
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .put("/{id}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("message", equalTo(String.format(MSG_EMAIL_ALREADY_IN_USE, user.getEmail())));
    }

    private String getJsonFromUser(User user) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (user.getId() != null) json.append("\"id\": \"").append(user.getId()).append("\",");
        json.append("\"name\": \"").append(user.getName()).append("\",");
        json.append("\"email\": \"").append(user.getEmail()).append("\",");
        json.append("\"phone\": \"").append(user.getPhone()).append("\",");
        json.append("\"birthDate\": \"").append(sdf.format(user.getBirthDate())).append("\",");
        json.append("\"userType\": \"").append(user.getUserType().name()).append("\"");
        json.append("}");
        return json.toString();
    }
}
