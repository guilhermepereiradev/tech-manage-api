delete from _user;
alter sequence public._user_id_seq restart with 1;

insert into _user (full_name, email, phone, birth_date, user_type)
values ('Frank Castle', 'frankcastle@email.com', '+55 11 99999-9999', '1970-06-28', 'ADMIN');

insert into _user (full_name, email, phone, birth_date, user_type)
values ('Jessica Jones', 'jessicajones@email.com', '+55 11 99999-9998', '1981-02-24', 'EDITOR');

insert into _user (full_name, email, phone, birth_date, user_type)
values ('Matt Murdock', 'mattmurdock@email.com', '+55 11 99999-9997', '1986-10-15', 'VIEWER');