package com.monk.ecom.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Cart {

    private List<Product> items;

    private float totalPrice;

    private float totalDiscount;

    private float finalPrice;
}
