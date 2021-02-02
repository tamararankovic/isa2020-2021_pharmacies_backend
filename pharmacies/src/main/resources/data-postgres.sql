insert into users(id, name, surname, email, password, role) values (1, 'Tamara', 'Rankovic', 'email1@gmail.com', '1234', 0);
insert into users(id, name, surname, email, password, role) values (2, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1);
insert into users(id, name, surname, email, password, role) values (3, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2);
insert into users(id, name, surname, email, password, role) values (4, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3);
insert into users(id, name, surname, email, password, role) values (5, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4);
insert into users(id, name, surname, email, password, role) values (6, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5);

insert into pharmacy(id, name, description, address) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');
insert into pharmacy(id, name, description, address) values (2, 'Pharmacy 2', 'Some description of pharmacy 2', 'Trg Dositeja Obradovica 6, Novi Sad');

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 1);
insert into engagement_in_pharmacy(id, pharmacy_id) values (2, 1);
insert into engagement_in_pharmacy(id, pharmacy_id) values (3, 2);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (1, 1, 2);

insert into pharmacy_admin(id, pharmacy_id, user_id) values (1, 1, 6);

insert into dermatologist(id, user_id) values (1, 3);
insert into dermatologist_engegement_in_pharmacies(dermatologist_id, engegement_in_pharmacies_id) values (1, 2);
insert into dermatologist_engegement_in_pharmacies(dermatologist_id, engegement_in_pharmacies_id) values (1, 3);