CREATE TABLE Product_Coupon_Map (
    coupon_id BIGINT PRIMARY KEY,
    product_id BIGINT NOT NULL,
    total_price NUMERIC(9,2),
    discount NUMERIC(9,2),
    discounted_price NUMERIC(9,2)
);

CREATE TABLE Buy_And_Get_Coupon_Map (
    buy_and_get_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    coupon_Id BIGINT,
    buy_Product_Id BIGINT NOT NULL,
    buy_Product_Quantity INT NOT NULL,
    avail_Product_Id BIGINT NOT NULL,
    avail_Product_Quantity INT NOT NULL,
    discount NUMERIC(5, 2) NOT NULL,
    repetition_limit INT NOT NULL
);