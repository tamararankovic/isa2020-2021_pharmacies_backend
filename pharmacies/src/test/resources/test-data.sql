insert into users(id, name, surname, email, password, role, active, loged) values (1, 'Tamara', 'Rankovic', 'eminaturkovic600@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (2, 'Tamara', 'Rankovic', 'email2@gmail.com', '1234', 1, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (3, 'Tamara', 'Turkovic', 'email3@gmail.com', '1234', 2, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (4, 'Tamara', 'Rankovic', 'rankovictamaraa@gmail.com', '1234', 3, TRUE, FALSE);
insert into users(id, name, surname, email, password, role, active, loged) values (5, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (6, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (7, 'Milijana', 'Djordjevic', 'rankovictamaraa+3@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (8, 'Milijana', 'Djordjevic', 'milijana.djordjevic1998+1@gmail.com', '1234', 0, TRUE, TRUE);

insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (1, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 1);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (2, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 7);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (3, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 8);

insert into pharmacy(id, name, description, address, version) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad', 1);
insert into pharmacy(id, name, description, address, version) values (2, 'Pharmacy 2', 'Some description of pharmacy 2', 'Trg Dositeja Obradovica 6, Novi Sad', 1);

insert into engagement_in_pharmacy(id, pharmacy_id) values (1, 1);
insert into engagement_in_pharmacy(id, pharmacy_id) values (2, 1);
insert into engagement_in_pharmacy(id, pharmacy_id) values (3, 2);

insert into daily_engagement(id, day_of_week, end_time, start_time) values(1, 0, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(2, 1, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(3, 2, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(4, 3, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(5, 4, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(6, 0, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(7, 1, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(8, 2, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(9, 3, '13:00:00.000000', '08:00:00.000000');
insert into daily_engagement(id, day_of_week, end_time, start_time) values(10, 4, '13:00:00.000000', '08:00:00.000000');

insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(1, 1);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(1, 2);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(1, 3);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(1, 4);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(1, 5);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(2, 6);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(2, 7);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(2, 8);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(2, 9);
insert into engagement_in_pharmacy_daily_engagements(engagement_in_pharmacy_id, daily_engagements_id) values(2, 10);

insert into pharmacist(id, engegement_in_pharmacy_id, user_id, currently_has_appointment, version) values (1, 1, 2, '0', 1);

insert into pharmacy_admin(id, pharmacy_id, user_id) values (1, 1, 6);

insert into dermatologist(id, user_id, currently_has_appointment, version) values (1, 3, '0', 1);
insert into dermatologist_engegement_in_pharmacies(dermatologist_id, engegement_in_pharmacies_id) values (1, 2);
insert into dermatologist_engegement_in_pharmacies(dermatologist_id, engegement_in_pharmacies_id) values (1, 3);

insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (1, 'info', 1, 'LEK1', 0, 'Manufacturer1', 'Lek1', 2, 'mucnina', 0, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (2, 'info', 1, 'LEK2', 1, 'Manufacturer2', 'Lek2', 2, 'glavobolja', 1, '0');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (3, 'info', 1, 'LEK3', 2, 'Manufacturer3', 'Lek3', 2, 'glavobolja', 2, '1');

insert into patient_allergies(patient_id, allergies_id) values(1, 1);

insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (1, 30, 30, '1', 200, '1', '2021-02-08 08:30:00.000000', 1, 1, 1, '1',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (2, 30, 30, '0', 200, '0', '2021-02-23 08:30:00.000000', 1, null, 1, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (3, 30, 30, '0', 200, '0', '2021-02-24 08:30:00.000000', 1, null, 1, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (4, 30, 30, '0', 200, '1', '2021-02-26 08:30:00.000000', 1, 3, 2, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (5, 30, 30, '1', 200, '1', '2021-02-08 09:30:00.000000', 1, 3, 1, '1',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (6, 30, 30, '0', 200, '1', '2019-02-08 08:30:00.000000', 1, 1, 1, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (7, 30, 30, '0', 200, '1', '2021-02-26 08:30:00.000000', 1, 1, 1, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (8, 30, 30, '0', 200, '1', '2021-04-23 08:30:00.000000', 1, 1, 1, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (9, 30, 30, '0', 200, '1', '2021-02-26 08:30:00.000000', 1, 3, 2, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (10, 30, 30, '0', 200, '1', '2021-02-08 09:30:00.000000', 1, 3, 1, '0',5, 1);
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done, points_after_appointment, version) values (11, 30, 30, '0', 200, '1', '2021-02-10 17:40:00.000000', 1, 1, 1, '0',5, 1);