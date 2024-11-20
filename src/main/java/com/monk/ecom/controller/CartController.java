package com.monk.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.service.ApplicableCouponService;

@RestController
public class CartController {

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @PostMapping("/applicable-coupons")
    public List<Coupon> fetchApplicableCoupons(@RequestBody CartDetails cart) {

        List<Coupon> applicableCoupon = applicableCouponService.fetchApplicableCoupon(cart);
        return applicableCoupon;
    }

}
