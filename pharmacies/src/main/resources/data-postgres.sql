insert into users(id, name, surname, email, password, role) values (1, 'Tamara', 'Rankovic', 'email1@gmail.com', '1234', 0);
insert into users(id, name, surname, email, password, role) values (2, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1);
insert into users(id, name, surname, email, password, role) values (3, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2);
insert into users(id, name, surname, email, password, role) values (4, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3);
insert into users(id, name, surname, email, password, role) values (5, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4);
insert into users(id, name, surname, email, password, role) values (6, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5);
insert into users(id, name, surname, email, password, role) values (7, 'Tamara', 'Rankovic', 'email7@gmail.com', '1234', 0);

insert into pharmacy(id, name, description, address) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 1);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (1, 1, 2);
insert into patient values (1, 'Marka Miljanova 5', 0 ,'Beograd', 'Srbija',0,'+381452789', 0, 7);
insert into patient values (2, 'Mise Dimitrijevica 45', 0 ,'Novi Sad', 'Srbija',0,'+3814527898', 0, 1);

insert into medicine values (1,	'a',1,	'aa',	0,'a',	'paracetamol',	2,	'1',	0,	false);
insert into medicine values (2,	'a',1,	'bb',	0,'b',	'brufen',	2,	'1',	0,	false);
