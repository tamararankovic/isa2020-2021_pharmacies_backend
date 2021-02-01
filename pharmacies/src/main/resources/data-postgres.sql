insert into users(id, name, surname, email, password, role, active) values (201, 'Tamara', 'Rankovic', 'email1@gmail.com', '1234', 0, TRUE);
insert into users(id, name, surname, email, password, role, active) values (202, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1, TRUE);
insert into users(id, name, surname, email, password, role, active) values (203, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2, TRUE);
insert into users(id, name, surname, email, password, role, active) values (204, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3, TRUE);
insert into users(id, name, surname, email, password, role, active) values (205, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4, TRUE);
insert into users(id, name, surname, email, password, role, active) values (206, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5, TRUE);

insert into pharmacy(id, name, description, address) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 1);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (1, 1, 202);