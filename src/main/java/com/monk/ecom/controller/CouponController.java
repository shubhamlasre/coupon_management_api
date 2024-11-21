package com.monk.ecom.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.exception.CouponNotFoundException;
import com.monk.ecom.exception.InvalidInputException;
import com.monk.ecom.service.RetrieveCouponService;
import com.monk.ecom.service.UpdateCouponService;
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

    @Autowired
    private UpdateCouponService updateCouponService;

    // @RequestMapping(value = "/coupon", method = RequestMethod.POST)
    @PostMapping("/coupon")
    public String createCoupon(@RequestBody CreationCriteria couponCriteria) throws InvalidInputException {
        String type = couponCriteria.getTypeOfCoupon();
        if (type.equalsIgnoreCase("cart-wise")) {
            cartWiseCouponService.createCoupon(couponCriteria);
        } else if (type.equalsIgnoreCase("Product-wise")) {
            productWiseCouponService.createCoupon(couponCriteria);
        } else if (type.equalsIgnoreCase("BxGy")) {
            bxgyCouponService.createCoupon(couponCriteria);
        } else {
            throw new InvalidInputException("Coupon type " + type + " does not exist");
        }
        String message = type + " coupon created Successfully";
        return message;
    }

    // @RequestMapping(value = "/coupon", method = RequestMethod.GET)
    @GetMapping("/coupon")
    public List<Coupon> fetchCoupon() throws CouponNotFoundException, ConditionNotMetException {
        List<Coupon> coupons = retrieveCouponService.fetchAllCoupon();
        if (coupons.isEmpty()) {
            throw new CouponNotFoundException("No Coupons Exist");
        }
        return coupons;
    }

    @GetMapping("/coupon/{id}")
    public Coupon fetchCoupon(@PathVariable int id) throws CouponNotFoundException, ConditionNotMetException {
        Coupon coupon = retrieveCouponService.fetchCoupon(id);
        return coupon;
    }

    @PutMapping("coupon/{id}")
    public String updateCoupon(@PathVariable long id, @RequestBody CreationCriteria couponCriteria) throws CouponNotFoundException {
        updateCouponService.updateCoupon(id, couponCriteria);
        return "Successfully updated Coupon with couponId: " + id;
    }

    @DeleteMapping("coupon/{id}")
    public String deleteCoupon(@PathVariable long id) throws CouponNotFoundException {
        updateCouponService.deleteCoupon(id);
        return "Successfully deleted Coupon with couponId: " + id;
    }

}
