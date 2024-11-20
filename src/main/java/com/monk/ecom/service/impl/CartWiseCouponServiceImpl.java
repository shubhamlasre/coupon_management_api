package com.monk.ecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CouponForCart;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.CouponForCartRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.service.CouponService;
import com.monk.ecom.util.couponUtility;

@Service
public class CartWiseCouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private CouponForCartRepository couponForCartRepo;

    @Autowired
    private couponUtility utility;

    @Override
    public Coupon createCoupon(CreationCriteria couponCreationCriteria) {
        ProductDetails couponDetails = couponCreationCriteria.getDetails();
        float discount = couponDetails.getDiscount();
        String description = utility.getCartWiseDiscountDescription(couponDetails);
        Coupon couponResult = new Coupon(couponCreationCriteria.getTypeOfCoupon(), discount, description);
        Coupon savedCoupon = couponRepo.save(couponResult);
        CouponForCart couponForCart = new CouponForCart(savedCoupon.getId(), couponDetails.getThreshold(), discount,
                description);
        couponForCartRepo.save(couponForCart);
        return couponResult;
    }

}