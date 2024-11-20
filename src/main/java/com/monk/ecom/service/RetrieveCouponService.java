package com.monk.ecom.service;

import java.util.List;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.exception.ConditionNotMetException;

public interface RetrieveCouponService {

    public List<Coupon> fetchAllCoupon() throws ConditionNotMetException;

    public Coupon fetchCoupon(long id) throws ConditionNotMetException;

}
