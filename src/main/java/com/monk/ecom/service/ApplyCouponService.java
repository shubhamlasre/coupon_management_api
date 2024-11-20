package com.monk.ecom.service;

import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.result.CartResult;

public interface ApplyCouponService {

    public CartResult applyCouponOnCart(long couponId, CartDetails cartDetails);
}
