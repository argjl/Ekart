-- Creating Users Table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);

-- Creating Sellers Table
CREATE TABLE sellers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shopAddress VARCHAR(255),
    shopName VARCHAR(255),
    emailSeller VARCHAR(255),
    phoneNumberSeller VARCHAR(255),
    CONSTRAINT fk_seller_user FOREIGN KEY (id) REFERENCES users(id)
);

-- Creating Customers Table
CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    emailCustomer VARCHAR(255),
    phoneNumberCustomer BIGINT,
    CONSTRAINT fk_customer_user FOREIGN KEY (id) REFERENCES users(id)
);

-- Creating Categories Table
CREATE TABLE category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    categoryName VARCHAR(255)
);

-- Creating Products Table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255),
    price BIGINT,
    quantity INT,
    seller_id BIGINT,
    category BIGINT,
    CONSTRAINT fk_product_seller FOREIGN KEY (seller_id) REFERENCES sellers(id),
    CONSTRAINT fk_product_category FOREIGN KEY (category) REFERENCES category(id)
);

-- Creating Carts Table
CREATE TABLE cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT,
    CONSTRAINT fk_cart_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

-- Creating Cart Items Table
CREATE TABLE cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    quantity INT,
    cart_id BIGINT,
    CONSTRAINT fk_cartitem_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_cartitem_cart FOREIGN KEY (cart_id) REFERENCES cart(id)
);

-- Creating Orders Table
CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orderDate TIMESTAMP,
    price BIGINT,
    customer_id BIGINT,
    seller_id BIGINT,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT fk_order_seller FOREIGN KEY (seller_id) REFERENCES sellers(id)
);

-- Creating Order Items Table
CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    quantity INT,
    order_id BIGINT,
    CONSTRAINT fk_orderitem_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_orderitem_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
