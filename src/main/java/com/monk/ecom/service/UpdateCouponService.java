package com.monk.ecom.service;

import com.monk.ecom.domain.CreationCriteria;

public interface UpdateCouponService {

    public void updateCoupon(long id, CreationCriteria couponCriteria);

    public void deleteCoupon(long id);
}
