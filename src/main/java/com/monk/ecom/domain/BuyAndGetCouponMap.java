package com.monk.ecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class BuyAndGetCouponMap {

    @Id
    private long BuyAndGetId;

    private long couponId;

    private long buyProductId;

    private int buyProductQuantity;

    private long availProductId;

    private int availProductQuantity;

    private float discount;

    public BuyAndGetCouponMap(long couponId, long buyProductId, int buyProductQuantity, long availProductId,
            int availProductQuantity, float discount) {
        this.availProductId = availProductId;
        this.availProductQuantity = availProductQuantity;
        this.buyProductId = buyProductId;
        this.buyProductQuantity = buyProductQuantity;
        this.couponId = couponId;
        this.discount = discount;
    }

}
