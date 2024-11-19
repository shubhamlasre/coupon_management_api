package com.monk.ecom.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Product {

    private long productId;
    private int quantity;
    private float price;
    private float totalDiscount;
}