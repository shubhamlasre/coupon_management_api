package com.monk.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.service.impl.CartWiseCouponServiceImpl;

@RestController
public class CouponController {

    @Autowired
    private CartWiseCouponServiceImpl cartWiseCouponService;

    @RequestMapping(value = "/coupon", method = RequestMethod.POST)
    public String createCoupon(@RequestBody CreationCriteria couponCriteria) {
        cartWiseCouponService.createCoupon(couponCriteria);
        String message = couponCriteria.getTypeOfCoupon() + " coupon created Successfully";
        return message;
    }

    @RequestMapping(value = "/coupon", method = RequestMethod.GET)
    public String fetchCoupon() {
        // cartWiseCouponService.createCoupon(couponCriteria);
        String message = " All coupons fetched Successfully";
        return message;
    }

}
