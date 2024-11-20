package com.monk.ecom.domain.result;

import com.monk.ecom.domain.Cart;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CartResult {

    private Cart updatedCart;
}
