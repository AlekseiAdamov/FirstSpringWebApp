DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS customers_products;

CREATE TABLE customers
(
    ID   BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(128) NOT NULL
);

CREATE TABLE products
(
    ID    BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME  VARCHAR(128) NOT NULL,
    PRICE DOUBLE       NOT NULL
);

CREATE TABLE customers_products
(
    CUSTOMER_ID BIGINT,
    PRODUCT_ID  BIGINT
);

INSERT INTO customers (NAME)
VALUES ('John Wayne'),
       ('Jack Tuesday'),
       ('Lisa Simpson'),
       ('Monica Warwick');

INSERT INTO products (NAME, PRICE)
VALUES ('Apples', 2.5),
       ('Oranges', 3.5),
       ('Beef', 10.25),
       ('Cabbage', 1.75),
       ('Potatoes', 1.25),
       ('Carrot', 1.5),
       ('Strawberry', 5.5),
       ('Watermelons', 1.2),
       ('Tofu', 5.45),
       ('Radish', 2.15);

INSERT INTO customers_products
VALUES (1, 1),
       (1, 3),
       (1, 5),
       (2, 2),
       (2, 3),
       (2, 6),
       (2, 9),
       (3, 1),
       (3, 2),
       (3, 4),
       (3, 7),
       (3, 8),
       (4, 4);
