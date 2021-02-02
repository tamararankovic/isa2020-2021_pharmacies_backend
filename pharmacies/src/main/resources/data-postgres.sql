insert into users(id, name, surname, email, password, role, active) values (301, 'Tamara', 'Rankovic', 'milijana.djordjevic1998@gmail.com', '1234', 0, TRUE);
insert into users(id, name, surname, email, password, role, active) values (302, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1, TRUE);
insert into users(id, name, surname, email, password, role, active) values (303, 'Tamara', 'Rankovic', 'email3@gmail.com', '1234', 2, TRUE);
insert into users(id, name, surname, email, password, role, active) values (304, 'Tamara', 'Rankovic', 'email4@gmail.com', '1234', 3, TRUE);
insert into users(id, name, surname, email, password, role, active) values (305, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4, TRUE);
insert into users(id, name, surname, email, password, role, active) values (306, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5, TRUE);
insert into users(id, name, surname, email, password, role, active) values (307, 'Milijana', 'Djordjevic', 'email7@gmail.com', '1234', 0, TRUE);

insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (301, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 301);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (302, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 307);


insert into pharmacy(id, name, description, address) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 1);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (301, 1, 302);

insert into dermatologist(id, user_id) values (301, 303);

insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id) values (1, 30, 30, '0', 2000, '0', '2021-02-02 08:00:00.000000', 301, 301, 1);

insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (1, 'info', 1, 'LEK1', 0, 'Manufacturer1', 'Lek1', 1, '', 0, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (2, 'info', 1, 'LEK2', 1, 'Manufacturer2', 'Lek2', 1, '', 1, '0');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (3, 'info', 1, 'LEK3', 2, 'Manufacturer3', 'Lek3', 1, '', 2, '1');

insert into patient_allergies(patient_id, allergies_id) values(301, 1);
insert into patient_allergies(patient_id, allergies_id) values(302, 1);