CREATE TABLE Coupon_For_Cart (
    coupon_id BIGINT PRIMARY KEY,
    threshold NUMERIC(9,2),
    discount NUMERIC(9,2),
    description VARCHAR(255)
);