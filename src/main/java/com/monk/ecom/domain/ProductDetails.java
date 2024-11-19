package com.monk.ecom.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ProductDetails {

    private float price;
    private float discount;
    private long productId;
    private List<Product> buyProducts;
    private List<Product> getProducts;
    private int repetitionLimit;
}
