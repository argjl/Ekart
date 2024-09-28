-- User Table (Base class for Customer and Seller)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- Customer Table (Inherits from User)
CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Seller Table (Inherits from User)
CREATE TABLE sellers (
    id BIGINT PRIMARY KEY,
    shop_address VARCHAR(255),
    shop_name VARCHAR(255),
    email_seller VARCHAR(100),
    phone_number_seller VARCHAR(50),
    FOREIGN KEY (id) REFERENCES users(id)
);

-- Category Table
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100)
);

-- Product Table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(500),
    price BIGINT,
    quantity INT,
    seller_id BIGINT,
    category_id BIGINT,
    FOREIGN KEY (seller_id) REFERENCES sellers(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Cart Table
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- CartItem Table
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    quantity INT,
    cart_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (cart_id) REFERENCES cart(id)
);

-- Order Table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date TIMESTAMP,
    price BIGINT,
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- OrderItems Table
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    quantity INT,
    order_id BIGINT,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE
);
