package com.monk.ecom.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CreationCriteria {
    private String typeOfCoupon;
    private ProductDetails details;
}
