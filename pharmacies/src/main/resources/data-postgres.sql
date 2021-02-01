insert into users(id, name, surname, email, password, role) values (1, 'Tamara', 'Rankovic', 'email1@gmail.com', '1234', 0);
insert into users(id, name, surname, email, password, role) values (2, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1);
insert into users(id, name, surname, email, password, role) values (3, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2);
insert into users(id, name, surname, email, password, role) values (4, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3);
insert into users(id, name, surname, email, password, role) values (5, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4);
insert into users(id, name, surname, email, password, role) values (6, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5);

insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (1, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 1);

insert into pharmacy(id, name, description, address) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 1);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (1, 1, 2);