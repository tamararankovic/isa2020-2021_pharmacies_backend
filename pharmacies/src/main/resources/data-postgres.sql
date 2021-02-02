insert into users(id, name, surname, email, password, role, active, loged) values (301, 'Tamara', 'Rankovic', 'email1@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (302, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (303, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (304, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3, TRUE, FALSE);
insert into users(id, name, surname, email, password, role, active, loged) values (305, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (306, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (307, 'Milijana', 'Djordjevic', 'email7@gmail.com', '1234', 0, TRUE, TRUE);

insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (301, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 301);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (302, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 307);

insert into pharmacy(id, name, description, address) values (301, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 301);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (301, 1, 302);