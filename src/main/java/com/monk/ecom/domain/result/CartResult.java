package com.monk.ecom.domain.result;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monk.ecom.domain.Cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartResult {

    @JsonProperty("updated_cart")
    private Cart updatedCart;
}
