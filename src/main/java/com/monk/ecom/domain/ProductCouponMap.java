package com.monk.ecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProductCouponMap {

    @Id
    private long couponId;

    private long productId;

    private float totalPrice;

    private float discount;

    private float discountedPrice;

}
