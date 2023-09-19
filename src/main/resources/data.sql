-- Insert data into the 'users' table to assign an admin and an employer
INSERT INTO users (username, password, enabled, apikey, email) VALUES ('clide', '$2a$12$DFgxZnJm8dnjqmVKStxXT.dTnwfUnphh17Htz2HUtkP7W5Sa1dKV2', true, '7847493', 'programming@home.nl');
INSERT INTO users (username, password, enabled, apikey, email) VALUES ('employer', '$2a$12$wrPUVF5cG.3tKDO3b1c3ouPaDz.jwTky/n0zJJKxyGf3SpiVohooy', true, '7841233', 'working@store.nl');

-- Assign a role to the two 'users'
INSERT INTO authorities (username, authority) VALUES ('clide', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('employer', 'ROLE_EMPLOYER');


-- Insert data into the 'workschedules' table
INSERT INTO work_schedules(start_date, end_date, hours_per_week)VALUES ('2023-8-28', '2023-9-2', 9);

--Insert data into the 'products' table
INSERT INTO products(article_number, product_name, unit_price, available_stock, category)VALUES (12489, 'Infinity Feelings', 1185.99, 3, 'Engagement Rings');
INSERT INTO products(article_number, product_name, unit_price, available_stock, category)VALUES (113, 'Unity', 2349.00, 9, 'Silver Chains');
INSERT INTO products(article_number, product_name, unit_price, available_stock, category)VALUES (007, 'Sranang Boey', 989.99, 5, 'Golden Bracelet');
INSERT INTO products(article_number, product_name, unit_price, available_stock, category)VALUES (31512, 'Diamant Diver', 8735.99, 1, 'Elite Watch');
INSERT INTO products(article_number, product_name, unit_price, available_stock, category)VALUES (045, 'Its me Rolex', 25799.99, 1, 'Rolex Watch');


--Insert data into the 'inventories' table
INSERT INTO inventories( name, description, quantity)VALUES ('Watches', 'High quality watches with above average prices.', 2);
INSERT INTO inventories( name, description, quantity)VALUES ('Rings', 'Silver engagement rings.', 3);