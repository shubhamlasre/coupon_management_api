package com.monk.ecom.service;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;

public interface CouponService {
    public Coupon createCoupon(CreationCriteria couponCreationCriteria);
}
