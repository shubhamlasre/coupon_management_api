CREATE TABLE Coupon (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    discount NUMERIC(9,2),
    description VARCHAR(255),
    applicable_till DATE, 
    buy_product_ids VARCHAR(255),
    buy_product_quantities VARCHAR(255),
    get_product_ids VARCHAR(255),
    get_product_quantities VARCHAR(255)
);