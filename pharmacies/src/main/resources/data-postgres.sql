insert into users(id, name, surname, email, password, role, active, loged) values (1, 'Tamara', 'Rankovic', 'email1@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (2, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (3, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (4, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3, TRUE, FALSE);
insert into users(id, name, surname, email, password, role, active, loged) values (5, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (6, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (7, 'Milijana', 'Djordjevic', 'email7@gmail.com', '1234', 0, TRUE, TRUE);

insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (1, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 1);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (2, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 7);


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

insert into medicine values (1,	'a',1,	'aa',	0,'a',	'paracetamol',	2,	'1',	0,	false);
insert into medicine values (2,	'a',1,	'bb',	0,'b',	'brufen',	2,	'1',	0,	false);

alter sequence users_id_seq restart with 8;
alter sequence pharmacy_id_seq restart with 3;
alter sequence engagement_in_pharmacy_id_seq restart with 4;
alter sequence pharmacist_id_seq restart with 2;
alter sequence pharmacy_admin_id_seq restart with 2;
alter sequence dermatologist_id_seq restart with 2;
alter sequence medicine_id_seq restart with 3;
alter sequence patient_id_seq restart with 3;

