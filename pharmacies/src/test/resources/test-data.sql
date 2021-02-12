insert into users(id, name, surname, email, password, role, active, loged) values (1, 'Tamara', 'Rankovic', 'isa.mejl.za.usere@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (2, 'Tamara', 'Rankovic', 'isa.mejl.za.usere+1@gmail.com', '1234', 1, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (3, 'Tamara', 'Turkovic', 'isa.mejl.za.usere+2@gmail.com', '1234', 2, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (4, 'Tamara', 'Rankovic', 'isa.mejl.za.usere+3@gmail.com', '1234', 3, TRUE, FALSE);
insert into users(id, name, surname, email, password, role, active, loged) values (5, 'Tamara', 'Rankovic', 'isa.mejl.za.usere+4@gmail.com', '1234', 4, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (6, 'Tamara', 'Rankovic', 'isa.mejl.za.usere+5@gmail.com', '1234', 5, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (7, 'Milijana', 'Djordjevic', 'isa.mejl.za.usere+6@gmail.com', '1234', 0, TRUE, TRUE);
insert into users(id, name, surname, email, password, role, active, loged) values (8, 'Milijana', 'Djordjevic', 'isa.mejl.za.usere+7@gmail.com', '1234', 0, TRUE, TRUE);

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
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (4, 'info', 1, 'LEK4', 2, 'Manufacturer4', 'Lek4', 2, 'kostobolja', 2, '1');

insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (5, 'info', 1, 'LEK5', 1, 'Manufacturer5', 'Lek5', 2, 'glavobolja', 1, '0');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (6, 'info', 1, 'LEK6', 2, 'Manufacturer6', 'Lek6', 2, 'pospanost', 2, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (7, 'info', 1, 'LEK7', 2, 'Manufacturer7', 'Lek7', 2, 'kostobolja', 2, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (8, 'info', 1, 'LEK8', 1, 'Manufacturer8', 'Lek8', 2, 'glavobolja', 1, '0');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (9, 'info', 1, 'LEK9', 2, 'Manufacturer9', 'Lek9', 2, 'pospanost', 2, '1');
insert into medicine(id, additional_info, advised_daily_dose, code, form, manufacturer, name, points, side_effects, type, with_prescription) values (10, 'info', 1, 'LEK10', 2, 'Manufacturer10', 'Lek10', 2, 'kostobolja', 2, '1');

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

insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (1, 30, '0', '2021-02-26 08:00:00.000000', 1, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (2, 30, '0', '2021-02-26 09:00:00.000000', 3, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (3, 30, '0', '2021-02-08 10:30:00.000000', 1, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (4, 30, '0', '2021-02-09 11:30:00.000000', 1, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (5, 30, '1', '2021-02-05 10:30:00.000000', 1, 1, '1',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (6, 30, '1', '2021-02-04 11:00:00.000000', 2, 1, '1',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (7, 30, '0', '2020-02-08 08:00:00.000000', 1, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (8, 30, '0', '2020-02-10 09:00:00.000000', 3, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (9, 30, '0', '2021-05-08 10:30:00.000000', 1, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (10, 30, '0', '2020-10-09 11:30:00.000000', 1, 1, '0',5,100);
insert into pharmacist_appointment(id, default_duration_in_minutes, patient_was_present, start_date_time, patient_id, pharmacist_id, done,points_after_advising,price) values (11, 30, '0', '2021-02-10 17:45:00.000000', 1, 1, '0',5,100);

insert into medicine_ingredients(medicine_id, ingredients) values (1, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (1, 'ingredient2');
insert into medicine_ingredients(medicine_id, ingredients) values (1, 'ingredient3');
insert into medicine_ingredients(medicine_id, ingredients) values (2, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (2, 'ingredient2');
insert into medicine_ingredients(medicine_id, ingredients) values (3, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (5, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (6, 'ingredient2');
insert into medicine_ingredients(medicine_id, ingredients) values (7, 'ingredient1');
insert into medicine_ingredients(medicine_id, ingredients) values (8, 'ingredient3');
insert into medicine_ingredients(medicine_id, ingredients) values (9, 'ingredient3');
insert into medicine_ingredients(medicine_id, ingredients) values (10, 'ingredient3');

insert into medicine_quantity(id, quantity, medicine_id) values (1, 5, 1);
insert into medicine_quantity(id, quantity, medicine_id) values (2, 0, 2);
insert into medicine_quantity(id, quantity, medicine_id) values (3, 2, 3);
insert into medicine_quantity(id, quantity, medicine_id) values (4, 5, 4);
insert into medicine_quantity(id, quantity, medicine_id) values (5, 5, 4);
insert into medicine_quantity(id, quantity, medicine_id) values (6, 8, 5);
insert into medicine_quantity(id, quantity, medicine_id) values (7, 5, 6);
insert into medicine_quantity(id, quantity, medicine_id) values (8, 5, 7);

insert into medicine_quantity(id, quantity, medicine_id) values (9, 8, 8);
insert into medicine_quantity(id, quantity, medicine_id) values (10, 5, 9);
insert into medicine_quantity(id, quantity, medicine_id) values (11, 5, 10);
insert into medicine_quantity(id, quantity, medicine_id) values (12, 8, 5);
insert into medicine_quantity(id, quantity, medicine_id) values (13, 5, 3);
insert into medicine_quantity(id, quantity, medicine_id) values (14, 5, 1);

insert into medicine_compatible_medicine_codes(medicine_id, compatible_medicine_codes) values(3, 'LEK4');
insert into medicine_compatible_medicine_codes(medicine_id, compatible_medicine_codes) values(3, 'LEK2');
insert into medicine_compatible_medicine_codes(medicine_id, compatible_medicine_codes) values(6, 'LEK3');
insert into medicine_compatible_medicine_codes(medicine_id, compatible_medicine_codes) values(5, 'LEK7');

insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 1);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 2);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 3);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 4);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 5);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 6);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 7);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (1, 8);

insert into pharmacy_medicines(pharmacy_id, medicines_id) values (2, 9);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (2, 10);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (2, 11);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (2, 12);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (2, 13);
insert into pharmacy_medicines(pharmacy_id, medicines_id) values (2, 14);

insert into reservation(id, due_date, received, medicine_id, patient_id, pharmacy_id, price) values(1, '2021-02-25 08:00:00.000000', '0', 1, 1, 1, 100);
insert into reservation(id, due_date, received, medicine_id, patient_id, pharmacy_id, price) values(2, '2021-02-04 08:00:00.000000', '0', 2, 1, 1, 200);
insert into reservation(id, due_date, received, medicine_id, patient_id, pharmacy_id, price) values(3, '2021-02-25 08:00:00.000000', '1', 3, 1, 1, 300);

insert into supplier(id, user_id) values (1, 4);

insert into orders(id, deadline, admin_creator_id) values (1, '2021-02-04 20:21:00.000000', 1);

insert into offer(id, deadline,accepted, supplier_id, total_price) values (1, '2021-02-04 20:21:00.000000' ,FALSE, 1, 5000);

insert into orders_offers(order_id, offers_id) values (1, 1);
insert into orders_medicine_quantities(order_id, medicine_quantities_id) values (1, 5);

insert into subscription(id, patient_id, pharmacy_id, cancelled) values (1, 2, 1, FALSE);
insert into subscription(id, pharmacy_id, patient_id, cancelled) values (2, 1, 1, FALSE);

insert into pharmacist_leave_request(id, pharmacist_id, start_date, end_date, type, state) values (1, 1, '2021-02-24', '2021-02-28', 0, 2);

insert into dermatologist_leave_request(id, dermatologist_id, start_date, end_date, type, state) values (1, 1, '2021-02-24', '2021-02-28', 0, 2);

insert into system_admin(id, user_id) values (1, 5);

insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (1, 1, 1, '2021-02-02 20:21:00.000000');
insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (2, 1, 1, '2021-02-01 20:21:00.000000');
insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (3, 1, 1, '2020-02-02 20:21:00.000000');
insert into medicine_missing_notification(id, medicine_id, pharmacy_id, timestamp) values (4, 1, 1, '2021-01-15 20:21:00.000000');

insert into price_list(id, pharm_app_price, pharm_app_price_defined, derm_app_price, derm_app_price_defined, start_date) values (1, 100, TRUE, 200, TRUE, '2019-02-28');
insert into price_list(id, pharm_app_price, pharm_app_price_defined, derm_app_price, derm_app_price_defined, start_date) values (2, 200, TRUE, 300, TRUE, '2019-02-28');
insert into pharmacy_price_lists(pharmacy_id, price_lists_id) values (1, 1);
insert into pharmacy_price_lists(pharmacy_id, price_lists_id) values (2, 2);
insert into medicine_price(id, price, medicine_id) values (1, 100, 1);
insert into medicine_price(id, price, medicine_id) values (2, 200, 2);
insert into medicine_price(id, price, medicine_id) values (3, 300, 3);
insert into medicine_price(id, price, medicine_id) values (4, 400, 4);
insert into medicine_price(id, price, medicine_id) values (5, 500, 5);
insert into medicine_price(id, price, medicine_id) values (6, 600, 6);
insert into medicine_price(id, price, medicine_id) values (7, 700, 7);
insert into medicine_price(id, price, medicine_id) values (8, 800, 8);
insert into medicine_price(id, price, medicine_id) values (9, 900, 9);
insert into medicine_price(id, price, medicine_id) values (10, 1000, 10);

insert into medicine_price(id, price, medicine_id) values (11, 200, 8);
insert into medicine_price(id, price, medicine_id) values (12, 1000, 9);
insert into medicine_price(id, price, medicine_id) values (13, 600, 10);
insert into medicine_price(id, price, medicine_id) values (14, 300, 5);
insert into medicine_price(id, price, medicine_id) values (15, 300, 3);
insert into medicine_price(id, price, medicine_id) values (16, 50, 1);

insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 1);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 2);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 3);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 4);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 5);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 6);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 7);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 8);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 9);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (1, 10);

insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (2, 11);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (2, 12);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (2, 13);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (2, 14);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (2, 15);
insert into price_list_medicine_prices(price_list_id, medicine_prices_id) values (2, 16);

insert into medicine_consumption(id, medicine_id, pharmacy_id, quantity, date_created) values (1, 1, 1, 1, '2020-01-15');
insert into medicine_consumption(id, medicine_id, pharmacy_id, quantity, date_created) values (2, 2, 1, 1, '2020-03-15');
insert into medicine_consumption(id, medicine_id, pharmacy_id, quantity, date_created) values (3, 3, 1, 1, '2020-05-15');
insert into medicine_consumption(id, medicine_id, pharmacy_id, quantity, date_created) values (4, 4, 1, 1, '2020-07-15');
insert into medicine_consumption(id, medicine_id, pharmacy_id, quantity, date_created) values (5, 1, 1, 1, '2020-01-15');

insert into loyalty_points(id, points_after_appointment, points_after_advising, points_for_regular, discount_for_regular, points_for_silver, discount_for_silver, points_for_gold, discount_for_gold) values (1, 5, 5, 0, 0, 20, 10, 40, 30);

insert into dermatologist_complaint(id, text, dermatologist_id, reply, patient_id) values (1, 'jako los dermatolog', 1, '', 1);

insert into pharmacist_complaint(id, text, pharmacist_id, reply, patient_id) values (1, 'jako los farmaceut', 1, '', 1);

insert into pharmacy_complaint(id, text, pharmacy_id, reply, patient_id) values (1, 'jako losa apoteka', 1, '', 1);
