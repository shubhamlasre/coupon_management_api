package com.monk.ecom.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.BuyAndGetCouponMap;
import com.monk.ecom.domain.Cart;
import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CouponForCart;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductCouponMap;
import com.monk.ecom.domain.result.CartResult;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.exception.CouponNotFoundException;
import com.monk.ecom.repository.BuyAndGetCouponRepository;
import com.monk.ecom.repository.CouponForCartRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductCouponMapRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.ApplicableCouponService;
import com.monk.ecom.service.ApplyCouponService;

@Service
public class ApplyCouponServiceImpl implements ApplyCouponService {

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

    @Autowired
    private ApplicableCouponService applicableCouponService;

    @Override
    public CartResult applyCouponOnCart(long couponId, CartDetails cartDetails) throws ConditionNotMetException, CouponNotFoundException {
        List<Coupon> applicableCoupons = applicableCouponService.fetchApplicableCoupon(cartDetails);
        Optional<Coupon> coupon = couponRepo.findById(couponId);
        CartResult cartResult = new CartResult();
        if (coupon.isPresent()) {
            Coupon appliedCoupon = coupon.get();
            if (appliedCoupon.getApplicableTill().isAfter(LocalDate.now())) {
                if (applicableCoupons.contains(appliedCoupon)) {
                    String couponType = appliedCoupon.getType();

                    if (couponType.equalsIgnoreCase("cart-wise")) {
                        cartResult = getCartResultForCartWise(couponId, cartDetails);
                    } else if (couponType.equalsIgnoreCase("product-wise")) {
                        cartResult = getCartResultForProductWise(couponId, cartDetails);
                    } else if (couponType.equalsIgnoreCase("BxGy")) {
                        cartResult = getCartResultForBuyAndGet(couponId, cartDetails);
                    }
                } else {
                    throw new ConditionNotMetException("Coupon with couponId: " + couponId + " is not applicable to this cart");
                }
            } else {
                throw new ConditionNotMetException("Coupon with couponId: " + couponId + " is expired");
            }
        }
        return cartResult;
    }

    private CartResult getCartResultForCartWise(long couponId, CartDetails cartDetails) throws ConditionNotMetException {
        CartResult cartResult = new CartResult();
        Cart finalCart = new Cart();
        List<Product> itemResult = new ArrayList<>();
        float totalPrice = 0;
        float finalDiscount = 0;
        float finalPrice = 0;

        Optional<CouponForCart> couponForCart = couponForCartRepo.findById(couponId);
        List<Product> items = cartDetails.getCart().getItems();
        float couponForCartDiscount = couponForCart.get().getDiscount();
        float totalDiscount = couponForCartDiscount / items.size();
        finalDiscount = couponForCartDiscount;
        for (Product cartProduct : items) {
            Product product = new Product(cartProduct.getProductId(), cartProduct.getPrice(), cartProduct.getQuantity(), totalDiscount);
            itemResult.add(product);
            totalPrice += product.getPrice();
        }
        finalCart.setItems(itemResult);
        finalCart.setTotalPrice(totalPrice);
        finalCart.setTotalDiscount(finalDiscount);
        finalPrice = totalPrice - finalDiscount;
        finalCart.setFinalPrice(finalPrice);
        cartResult.setUpdatedCart(finalCart);

        return cartResult;
    }

    private CartResult getCartResultForProductWise(long couponId, CartDetails cartDetails) throws ConditionNotMetException {
        CartResult cartResult = new CartResult();
        Cart finalCart = new Cart();
        List<Product> itemResult = new ArrayList<>();
        float totalPrice = 0;
        float finalDiscount = 0;
        float finalPrice = 0;

        Optional<ProductCouponMap> productCouponMapOptional = productCouponMapRepo.findById(couponId);
        ProductCouponMap productCouponMap = productCouponMapOptional.get();
        List<Product> items = cartDetails.getCart().getItems();
        finalDiscount = productCouponMap.getDiscount();
        for (Product cartProduct : items) {
            float totalDiscount = 0;
            if (productCouponMap.getProductId() == cartProduct.getProductId()) {
                totalDiscount = finalDiscount;
            }
            Product product = new Product(cartProduct.getProductId(), cartProduct.getPrice(), cartProduct.getQuantity(), totalDiscount);
            itemResult.add(product);
            totalPrice += product.getPrice();
        }
        finalCart.setItems(itemResult);
        finalCart.setTotalPrice(totalPrice);
        finalCart.setTotalDiscount(finalDiscount);
        finalPrice = totalPrice - finalDiscount;
        finalCart.setFinalPrice(finalPrice);
        cartResult.setUpdatedCart(finalCart);

        return cartResult;
    }

    private CartResult getCartResultForBuyAndGet(long couponId, CartDetails cartDetails) throws ConditionNotMetException {
        CartResult cartResult = new CartResult();
        Cart finalCart = new Cart();
        List<Product> itemResult = new ArrayList<>();
        float totalPrice = 0;
        float finalDiscount = 0;
        float finalPrice = 0;
        List<BuyAndGetCouponMap> buyAndGetCouponMapList = buyAndGetCouponRepo.findByCouponId(couponId);
        BuyAndGetCouponMap buyAndGetCouponMap = buyAndGetCouponMapList.get(0);
        long freeProductId = buyAndGetCouponMap.getAvailProductId();
        Product freeProduct = productRepo.findById(freeProductId).get();
        finalDiscount = freeProduct.getPrice() * buyAndGetCouponMap.getAvailProductQuantity();
        List<Product> items = cartDetails.getCart().getItems();
        for (Product cartProduct : items) {
            float totalDiscount = 0;
            if (freeProduct.getProductId() == cartProduct.getProductId()) {
                totalDiscount = finalDiscount;
            }
            Product product = new Product(cartProduct.getProductId(), cartProduct.getPrice(), cartProduct.getQuantity(), totalDiscount);
            itemResult.add(product);
            totalPrice += product.getPrice() * cartProduct.getQuantity();
        }
        finalCart.setItems(itemResult);
        finalCart.setTotalPrice(totalPrice);
        finalCart.setTotalDiscount(finalDiscount);
        finalPrice = totalPrice - finalDiscount;
        finalCart.setFinalPrice(finalPrice);
        cartResult.setUpdatedCart(finalCart);

        return cartResult;
    }

}
