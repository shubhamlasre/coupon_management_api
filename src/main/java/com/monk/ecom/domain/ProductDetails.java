package com.monk.ecom.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ProductDetails {

    @JsonProperty("threshold")
    private float threshold;

    @JsonProperty("discount")
    private float discount;

    @JsonProperty("product_id")
    private long productId;

    @JsonProperty("buy_products")
    private List<Product> buyProducts;

    @JsonProperty("get_products")
    private List<Product> getProducts;

    @JsonProperty("repetition_limit")
    private int repetitionLimit;
}
