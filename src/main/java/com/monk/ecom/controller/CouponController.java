package com.monk.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.result.CouponSearchResult;
import com.monk.ecom.service.RetrieveCouponService;
import com.monk.ecom.service.impl.BxGyCouponServiceImpl;
import com.monk.ecom.service.impl.CartWiseCouponServiceImpl;
import com.monk.ecom.service.impl.ProductWiseCouponServiceImpl;

@RestController
public class CouponController {

    @Autowired
    private CartWiseCouponServiceImpl cartWiseCouponService;

    @Autowired
    private ProductWiseCouponServiceImpl productWiseCouponService;

    @Autowired
    private BxGyCouponServiceImpl bxgyCouponService;

    @Autowired
    private RetrieveCouponService retrieveCouponService;

    // @RequestMapping(value = "/coupon", method = RequestMethod.POST)
    @PostMapping("/coupon")
    public String createCoupon(@RequestBody CreationCriteria couponCriteria) {
        String type = couponCriteria.getTypeOfCoupon();
        if (type.equalsIgnoreCase("cart-wise")) {
            cartWiseCouponService.createCoupon(couponCriteria);
        } else if (type.equalsIgnoreCase("Product-wise")) {
            productWiseCouponService.createCoupon(couponCriteria);
        } else if (type.equalsIgnoreCase("BxGy")) {
            bxgyCouponService.createCoupon(couponCriteria);
        }
        String message = type + " coupon created Successfully";
        return message;
    }

    // @RequestMapping(value = "/coupon", method = RequestMethod.GET)
    @GetMapping("/coupon")
    public CouponSearchResult fetchCoupon() {
        List<Coupon> coupons = retrieveCouponService.fetchAllCoupon();
        CouponSearchResult result = new CouponSearchResult(coupons);
        return result;
    }

}
