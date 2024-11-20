package com.monk.ecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CouponForCart {

    @Id
    private long couponId;

    private float threshold;

    private float discount;

    private String description;
}
