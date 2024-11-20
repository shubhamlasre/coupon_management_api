package com.monk.ecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.BuyAndGetCouponMap;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.BuyAndGetCouponRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.CouponService;
import com.monk.ecom.util.couponUtility;

@Service
public class BxGyCouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private BuyAndGetCouponRepository buyAndGetCouponRepo;

    @Autowired
    private couponUtility utility;

    @Override
    public Coupon createCoupon(CreationCriteria couponCreationCriteria) {
        ProductDetails couponDetails = couponCreationCriteria.getDetails();
        float discount = couponDetails.getDiscount();
        String description = utility.getBxGyDiscountDescription(couponDetails);
        Coupon couponResult = new Coupon(couponCreationCriteria.getTypeOfCoupon(), discount, description);
        Coupon coupon = couponRepo.save(couponResult);
        for (Product buyProd : couponDetails.getBuyProducts()) {
            for (Product getProd : couponDetails.getGetProducts()) {
                Product product = productRepo.findById(getProd.getProductId()).get();
                float finalDiscount = product.getPrice() * getProd.getQuantity();
                BuyAndGetCouponMap bngCouponMap = new BuyAndGetCouponMap(coupon.getId(), buyProd.getProductId(),
                        buyProd.getQuantity(), getProd.getProductId(), getProd.getQuantity(), finalDiscount);
                buyAndGetCouponRepo.save(bngCouponMap);
            }
        }
        return couponResult;
    }

}
