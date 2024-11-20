package com.monk.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.result.CartResult;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.exception.CouponNotFoundException;
import com.monk.ecom.service.ApplicableCouponService;
import com.monk.ecom.service.impl.ApplyCouponServiceImpl;

@RestController
public class CartController {

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @Autowired
    private ApplyCouponServiceImpl applyCouponServiceImpl;

    @PostMapping("/applicable-coupons")
    public List<Coupon> fetchApplicableCoupons(@RequestBody CartDetails cart) throws CouponNotFoundException {

        List<Coupon> applicableCoupon = applicableCouponService.fetchApplicableCoupon(cart);
        return applicableCoupon;
    }

    @PostMapping("/apply-coupons/{id}")
    public CartResult applyCoupons(@PathVariable long id, @RequestBody CartDetails cart) throws ConditionNotMetException, CouponNotFoundException {

        CartResult cartResult = applyCouponServiceImpl.applyCouponOnCart(id, cart);
        return cartResult;
    }

}
