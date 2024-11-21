package com.monk.ecom.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Cart {

    @JsonProperty("items")
    private List<Product> items;

    @JsonProperty("total_price")
    private float totalPrice;

    @JsonProperty("total_discount")
    private float totalDiscount;

    @JsonProperty("final_price")
    private float finalPrice;
}
