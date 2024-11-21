package com.monk.ecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class BuyAndGetCouponMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long BuyAndGetId;

    private long couponId;

    private long buyProductId;

    private int buyProductQuantity;

    private long availProductId;

    private int availProductQuantity;

    private float discount;

    private int repetitionLimit;

    public BuyAndGetCouponMap(long couponId, long buyProductId, int buyProductQuantity, long availProductId,
            int availProductQuantity, float discount, int repetitionLimit) {
        this.availProductId = availProductId;
        this.availProductQuantity = availProductQuantity;
        this.buyProductId = buyProductId;
        this.buyProductQuantity = buyProductQuantity;
        this.couponId = couponId;
        this.discount = discount;
        this.repetitionLimit = repetitionLimit;
    }

}
