-- Inserting Sample Users
INSERT INTO users (username, password, role) VALUES
('seller1', '$2a$12$z8nidaerH8bIh4xop2pVDOPK/BIkoqmEOudlZHkcUa3MQu2mtGyOq', 'SELLER'),
('customer1', '$2a$12$SwhXC3rIXb0xjIXriHbZfO2cgXupUqzpLXtecyPquVOfEUcNfhxVW', 'CUSTOMER');

-- Inserting Sample Sellers
INSERT INTO sellers (id, shopAddress, shopName, emailSeller, phoneNumberSeller) VALUES
(1, '123 Main St', 'Shop One', 'seller1@example.com', '1234567890');

-- Inserting Sample Customers
INSERT INTO customers (id, address, emailCustomer, phoneNumberCustomer) VALUES
(2, '456 Elm St', 'customer1@example.com', 9876543210);

-- Inserting Sample Categories
INSERT INTO category (categoryName) VALUES
('Electronics'),
('Clothing');

-- Inserting Sample Products
INSERT INTO products (name, description, price, quantity, seller_id, category) VALUES
('Smartphone', 'Latest model smartphone', 69999, 10, 1, 1),
('T-Shirt', 'Cotton t-shirt', 499, 50, 1, 2);

-- Inserting Sample Carts
INSERT INTO cart (customer_id) VALUES
(2);

-- Inserting Sample Cart Items
INSERT INTO cart_items (product_id, quantity, cart_id) VALUES
(1, 2, 1), -- 2 Smartphones
(2, 3, 1); -- 3 T-Shirts

-- Inserting Sample Orders
INSERT INTO orders (orderDate, price, customer_id, seller_id) VALUES
(NOW(), 70347, 2, 1); -- Order for the customer with id 2 from seller with id 1

-- Inserting Sample Order Items
INSERT INTO order_items (product_id, quantity, order_id) VALUES
(1, 2, 1), -- 2 Smartphones
(2, 1, 1); -- 1 T-Shirt
