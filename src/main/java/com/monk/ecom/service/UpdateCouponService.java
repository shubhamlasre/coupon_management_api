package com.monk.ecom.service;

import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.exception.CouponNotFoundException;

public interface UpdateCouponService {

    public void updateCoupon(long id, CreationCriteria couponCriteria) throws CouponNotFoundException;

    public void deleteCoupon(long id) throws CouponNotFoundException;
}
