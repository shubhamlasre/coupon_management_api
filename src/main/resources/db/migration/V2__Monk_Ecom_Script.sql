CREATE TABLE Product (
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    quantity INT,
    price NUMERIC(9,2)
);

INSERT INTO product (product_name, quantity, price) values('P1', 10, 10.0);
Insert into product (product_name, quantity, price) values('P2', 10, 20.0);
Insert into product (product_name, quantity, price) values('P3', 10, 30.0);
Insert into product (product_name, quantity, price) values('P4', 10, 40.0);
Insert into product (product_name, quantity, price) values('P5', 10, 50.0);
Insert into product (product_name, quantity, price) values('P6', 10, 60.0);
Insert into product (product_name, quantity, price) values('P7', 10, 70.0);
Insert into product (product_name, quantity, price) values('P8', 10, 80.0);
Insert into product (product_name, quantity, price) values('P9', 10, 90.0);
Insert into product (product_name, quantity, price) values('P10', 10, 100.0);