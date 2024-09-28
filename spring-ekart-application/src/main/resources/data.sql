-- Inserting into User Table
INSERT INTO users (id, username, password, email) VALUES
(1, 'john_doe', 'password123', 'john@example.com'),
(2, 'jane_seller', 'password456', 'jane_seller@example.com');

-- Inserting into Customer Table
INSERT INTO customers (id, first_name, last_name) VALUES
(1, 'John', 'Doe');

-- Inserting into Seller Table
INSERT INTO sellers (id, shop_address, shop_name, email_seller, phone_number_seller) VALUES
(2, '123 Main St', 'Jane''s Shop', 'jane_seller@example.com', '1234567890');

-- Inserting into Category Table
INSERT INTO category (id, category_name) VALUES
(1, 'Electronics'),
(2, 'Books');

-- Inserting into Product Table
INSERT INTO products (id, name, description, price, quantity, seller_id, category_id) VALUES
(1, 'Laptop', 'High-end gaming laptop', 1000, 10, 2, 1),
(2, 'Smartphone', 'Latest model smartphone', 800, 20, 2, 1),
(3, 'Book A', 'Interesting Book', 20, 50, 2, 2);

-- Inserting into Cart Table
INSERT INTO cart (id, customer_id) VALUES
(1, 1);

-- Inserting into CartItems Table
INSERT INTO cart_items (id, product_id, quantity, cart_id) VALUES
(1, 1, 1, 1),
(2, 3, 2, 1);

-- Inserting into Order Table
INSERT INTO orders (id, order_date, price, customer_id) VALUES
(1, CURRENT_TIMESTAMP, 1040, 1);

-- Inserting into OrderItems Table
INSERT INTO order_items (id, product_id, quantity, order_id) VALUES
(1, 1, 1, 1),
(2, 3, 2, 1);
