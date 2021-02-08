insert into users(id, name, surname, email, password, role, active, loged) values (1, 'Tamara', 'Rankovic', 'eminaturkovic600@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (2, 'Tamara', 'Rankovic', 'rankovictamaraa+1@gmail.com', '1234', 1, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (3, 'Tamara', 'Rankovic', 'rankovictamaraa+2@gmail.com', '1234', 2, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (4, 'Tamara', 'Rankovic', 'rankovictamaraa@gmail.com', '1234', 3, TRUE, FALSE);
insert into users(id, name, surname, email, password, role, active, loged) values (5, 'Tamara', 'Rankovic', 'email5@gmail.com', '1234', 4, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (6, 'Tamara', 'Rankovic', 'email6@gmail.com', '1234', 5, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (7, 'Milijana', 'Djordjevic', 'rankovictamaraa+3@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (8, 'Milijana', 'Djordjevic', 'milijana.djordjevic1998+1@gmail.com', '1234', 0, TRUE, TRUE);

insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (1, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 1);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (2, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 7);
insert into patient(id, address, category, city, country, penalties, phone, points, user_id) values (3, 'Podgoricka 2', 0, 'Novi Sad', 'Srbija', 0, '0655555555', 0, 8);

insert into pharmacy(id, name, description, address) values (1, 'Pharmacy 1', 'Some description of pharmacy 1', 'Masarikova 1, Novi Sad');
insert into pharmacy(id, name, description, address) values (2, 'Pharmacy 2', 'Some description of pharmacy 2', 'Trg Dositeja Obradovica 6, Novi Sad');


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
alter sequence daily_engagement_id_seq restart with 11;

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

insert into pharmacist(id, engegement_in_pharmacy_id, user_id) values (1, 1, 2);

insert into pharmacy_admin(id, pharmacy_id, user_id) values (1, 1, 6);

insert into dermatologist(id, user_id) values (1, 3);
insert into dermatologist_engegement_in_pharmacies(dermatologist_id, engegement_in_pharmacies_id) values (1, 2);
insert into dermatologist_engegement_in_pharmacies(dermatologist_id, engegement_in_pharmacies_id) values (1, 3);

insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (1, 'info', 1, 'LEK1', 0, 'Manufacturer1', 'Lek1', 2, 'mucnina', 0, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (2, 'info', 1, 'LEK2', 1, 'Manufacturer2', 'Lek2', 2, 'glavobolja', 1, '0');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (3, 'info', 1, 'LEK3', 2, 'Manufacturer3', 'Lek3', 2, 'glavobolja', 2, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (4, 'info', 1, 'LEK4', 2, 'Manufacturer4', 'Lek4', 2, 'kostobolja', 2, '1');

alter sequence users_id_seq restart with 9;
alter sequence pharmacy_id_seq restart with 3;
alter sequence engagement_in_pharmacy_id_seq restart with 4;
alter sequence pharmacist_id_seq restart with 2;
alter sequence pharmacy_admin_id_seq restart with 2;
alter sequence dermatologist_id_seq restart with 2;
alter sequence medicine_id_seq restart with 5;
alter sequence patient_id_seq restart with 4;

insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done) values (1, 30, 30, '0', 2000, '1', '2021-02-08 08:30:00.000000', 1, 1, 1, '0');
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done) values (2, 30, 30, '0', 2000, '0', '2021-02-23 08:30:00.000000', 1, null, 1, '0');
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done) values (3, 30, 30, '0', 2000, '0', '2021-02-24 08:30:00.000000', 1, null, 1, '0');
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done) values (4, 30, 30, '0', 2000, '0', '2021-02-25 08:30:00.000000', 1, 3, 2, '0');
insert into dermatologist_appointment(id, default_duration_in_minutes, duration_in_minutes, patient_was_present, price, scheduled, start_date_time, dermatologist_id, patient_id, pharmacy_id, done) values (5, 30, 30, '0', 2000, '1', '2021-02-08 09:30:00.000000', 1, 3, 1, '0');
alter sequence dermatologist_appointment_id_seq restart with 6;

insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done) values (1, 30, '0', '2021-02-08 08:00:00.000000', 1, 1, '0');
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done) values (2, 30, '0', '2021-02-08 09:00:00.000000', 3, 1, '0');
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done) values (3, 30, '0', '2021-02-08 10:30:00.000000', 1, 1, '0');
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done) values (4, 30, '0', '2021-02-09 11:30:00.000000', 1, 1, '0');
alter sequence pharmacist_appointment_id_seq restart with 5;

insert into medicine_ingredients(medicine_id, ingredients) values (1, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (1, 'ingredient2');
insert into medicine_ingredients(medicine_id, ingredients) values (1, 'ingredient3');
insert into medicine_ingredients(medicine_id, ingredients) values (2, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (2, 'ingredient2');
insert into medicine_ingredients(medicine_id, ingredients) values (3, 'ingredient1');

insert into medicine_quantity(id, quantity, medicine_id) values (1, 5, 1);
insert into medicine_quantity(id, quantity, medicine_id) values (2, 5, 2);
insert into medicine_quantity(id, quantity, medicine_id) values (3, 0, 3);
insert into medicine_quantity(id, quantity, medicine_id) values (4, 5, 4);
insert into medicine_quantity(id, quantity, medicine_id) values (5, 5, 4);
alter sequence medicine_quantity_id_seq restart with 6;

insert into medicine_compatible_medicine_codes(medicine_id, compatible_medicine_codes) values(3, 'LEK4');
insert into medicine_compatible_medicine_codes(medicine_id, compatible_medicine_codes) values(3, 'LEK2');

insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 1);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 2);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 3);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 4);

insert into patient_allergies(patient_id, allergies_id) values(1, 1);
insert into patient_allergies(patient_id, allergies_id) values(2, 1);

insert into reservation(id, due_date, received, medicine_id, patient_id, pharmacy_id) values(1, '2021-02-25 08:00:00.000000', '0', 1, 1, 1);
insert into reservation(id, due_date, received, medicine_id, patient_id, pharmacy_id) values(2, '2021-02-04 08:00:00.000000', '0', 2, 1, 1);
insert into reservation(id, due_date, received, medicine_id, patient_id, pharmacy_id) values(3, '2021-02-25 08:00:00.000000', '1', 3, 1, 1);

alter sequence pharmacist_appointment_id_seq restart with 4;
alter sequence reservation_id_seq restart with 4;


insert into supplier(id, user_id) values (1, 4);
alter sequence supplier_id_seq restart with 2;

insert into orders(id, deadline, admin_creator_id) values (1, '2021-02-04 20:21:00.000000', 1);
alter sequence orders_id_seq restart with 2;

insert into offer(id, deadline,accepted, supplier_id, total_price) values (1, '2021-02-04 20:21:00.000000' ,FALSE, 1, 5000);
alter sequence offer_id_seq restart with 2;

insert into orders_offers(order_id, offers_id) values (1, 1);
insert into orders_medicine_quantities(order_id, medicine_quantities_id) values (1, 5);

insert into subscription(id, patient_id, pharmacy_id, cancelled) values (1, 2, 1, FALSE);
alter sequence subscription_id_seq restart with 2;

insert into pharmacist_leave_request(id, pharmacist_id, start_date, end_date, type, state) values (1, 1, '2021-02-24 20:21:00.000000', '2021-02-28 20:21:00.000000', 0, 2);
alter sequence pharmacist_leave_request_id_seq restart with 2;

insert into dermatologist_leave_request(id, dermatologist_id, start_date, end_date, type, state) values (1, 1, '2021-02-24 20:21:00.000000', '2021-02-28 20:21:00.000000', 0, 2);
alter sequence dermatologist_leave_request_id_seq restart with 2;

insert into system_admin(id, user_id) values (1, 5);
alter sequence system_admin_id_seq restart with 2;

insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (1, 1, 1, '2021-02-02 20:21:00.000000');
insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (2, 1, 1, '2021-02-01 20:21:00.000000');
insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (3, 1, 1, '2020-02-02 20:21:00.000000');
insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (4, 1, 1, '2021-01-15 20:21:00.000000');
alter sequence medicine_missing_notification_id_seq restart with 5;
