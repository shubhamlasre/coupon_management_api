package com.monk.ecom.service;

import java.util.List;
import com.monk.ecom.domain.Coupon;

public interface RetrieveCouponService {

    public List<Coupon> fetchAllCoupon();

    public Coupon fetchCoupon(long id);

}
