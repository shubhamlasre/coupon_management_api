package com.monk.ecom.service;

import java.util.List;

import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.exception.CouponNotFoundException;

public interface ApplicableCouponService {

    public List<Coupon> fetchApplicableCoupon(CartDetails cartDetails) throws CouponNotFoundException;
}
