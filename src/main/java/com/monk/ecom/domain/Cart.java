package com.monk.ecom.domain;

import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Cart {

    List<Product> items;
}
