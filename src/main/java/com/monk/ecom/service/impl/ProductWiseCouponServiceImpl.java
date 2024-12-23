package com.monk.ecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductCouponMap;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductCouponMapRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.CouponService;
import com.monk.ecom.util.couponUtility;

@Service
public class ProductWiseCouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private ProductCouponMapRepository productCouponMapRepo;

    @Autowired
    private couponUtility utility;

    @Override
    public Coupon createCoupon(CreationCriteria couponCreationCriteria) {
        ProductDetails couponDetails = couponCreationCriteria.getDetails();
        float discount = couponDetails.getDiscount();
        String description = utility.getProductWiseDiscountDescription(couponDetails);
        Coupon couponResult = new Coupon(couponCreationCriteria.getTypeOfCoupon(), discount, description);
        couponResult.setApplicableTill(utility.getExpiryDate());
        Coupon savedCoupon = couponRepo.save(couponResult);
        Product product = productRepo.findById(couponDetails.getProductId()).get();
        ProductCouponMap productCouponMap = new ProductCouponMap(savedCoupon.getId(), couponDetails.getProductId(),
                product.getPrice(), discount, product.getPrice() - discount);
        productCouponMapRepo.save(productCouponMap);
        return couponResult;
    }

}
