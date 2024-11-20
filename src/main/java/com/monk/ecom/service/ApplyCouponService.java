package com.monk.ecom.service;

import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.result.CartResult;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.exception.CouponNotFoundException;

public interface ApplyCouponService {

    public CartResult applyCouponOnCart(long couponId, CartDetails cartDetails) throws ConditionNotMetException, CouponNotFoundException;
}
