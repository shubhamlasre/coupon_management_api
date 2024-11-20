package com.monk.ecom.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.BuyAndGetCouponMap;
import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CouponForCart;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductCouponMap;
import com.monk.ecom.repository.BuyAndGetCouponRepository;
import com.monk.ecom.repository.CouponForCartRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductCouponMapRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.ApplicableCouponService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ApplicableCouponServiceImpl implements ApplicableCouponService {

    @Autowired
    private CouponRepository couponRepo;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CouponForCartRepository couponForCartRepo;

    @Autowired
    private ProductCouponMapRepository productCouponMapRepo;

    @Autowired
    private BuyAndGetCouponRepository buyAndGetCouponRepo;

    @Override
    public List<Coupon> fetchApplicableCoupon(CartDetails cartDetails) {
        List<Coupon> applicableCoupons = new ArrayList<>();
        List<Product> cartItems = cartDetails.getCart().getItems();
        List<Long> uniqueCouponIds = new ArrayList<>();
        float cartValue = 0;
        for (Product product : cartItems) {
            // fetching product-wise coupon
            List<ProductCouponMap> productCouponMaps = productCouponMapRepo.findByProductId(product.getProductId());
            for (ProductCouponMap pcm : productCouponMaps) {
                if (!uniqueCouponIds.contains(pcm.getCouponId())) {
                    Optional<Coupon> coupon = couponRepo.findById(pcm.getCouponId());
                    if (coupon.isPresent()) {
                        applicableCoupons.add(coupon.get());
                        uniqueCouponIds.add(coupon.get().getId());
                    }
                }
            }

            // fetching buy and get coupon
            List<BuyAndGetCouponMap> buyAndGetCouponMaps = buyAndGetCouponRepo.findByBuyProductIdAndBuyProductQuantityLessThanEqual(
                    product.getProductId(), product.getQuantity());
            log.info(buyAndGetCouponMaps.toString());
            for (BuyAndGetCouponMap bngCouponMap : buyAndGetCouponMaps) {
                if (!uniqueCouponIds.contains(bngCouponMap.getCouponId())) {
                    Optional<Coupon> coupon = couponRepo.findById(bngCouponMap.getCouponId());
                    if (coupon.isPresent()) {
                        applicableCoupons.add(coupon.get());
                        uniqueCouponIds.add(coupon.get().getId());
                    }
                }
            }
            cartValue += product.getPrice();
        }
        // cartwise coupon
        List<CouponForCart> couponForCarts = couponForCartRepo.findByThresholdLessThanEqual(cartValue);
        for (CouponForCart couponForCart : couponForCarts) {
            if (!uniqueCouponIds.contains(couponForCart.getCouponId())) {
                Optional<Coupon> optCoupon = couponRepo.findById(couponForCart.getCouponId());
                applicableCoupons.add(optCoupon.get());
                uniqueCouponIds.add(optCoupon.get().getId());
            }

        }
        return applicableCoupons;
    }

}
