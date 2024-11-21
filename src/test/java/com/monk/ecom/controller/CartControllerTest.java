package com.monk.ecom.controller;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monk.ecom.domain.Cart;
import com.monk.ecom.domain.CartDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.result.CartResult;
import com.monk.ecom.domain.result.CouponSearchResult;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.exception.CouponNotFoundException;
import com.monk.ecom.service.ApplicableCouponService;
import com.monk.ecom.service.impl.ApplyCouponServiceImpl;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private ApplicableCouponService applicableCouponService;

    @Mock
    private ApplyCouponServiceImpl applyCouponServiceImpl;

    @Test
    void testFetchApplicableCoupons() throws CouponNotFoundException {
        CartDetails cart = new CartDetails();
        Coupon c1 = new Coupon("cart-wise", 10.0f, "10 off on 100");
        Coupon c2 = new Coupon("product-wise", 20.0f, "10 off on product P1");
        List<Coupon> mockCoupons = new ArrayList<>();
        mockCoupons.add(c1);
        mockCoupons.add(c2);
        when(applicableCouponService.fetchApplicableCoupon(cart)).thenReturn(mockCoupons);
        CouponSearchResult result = cartController.fetchApplicableCoupons(cart);

        assertNotNull(result);
        assertEquals(2, result.getCoupon().size());
        verify(applicableCouponService, times(1)).fetchApplicableCoupon(cart);
    }

    @Test
    void testApplyCoupons() throws ConditionNotMetException, CouponNotFoundException {

        long couponId = 1L;
        CartDetails cart = new CartDetails();
        Product p1 = new Product(1, 10.0f, 2, 0.0f);
        Product p2 = new Product(2, 20.0f, 2, 0.0f);
        List<Product> mockProducts = new ArrayList<>();
        Cart mockCart = new Cart();
        mockCart.setItems(mockProducts);
        mockCart.setTotalPrice(60.f);
        mockCart.setFinalPrice(50.0f);
        CartResult mockCartResult = new CartResult(mockCart);
        when(applyCouponServiceImpl.applyCouponOnCart(couponId, cart)).thenReturn(mockCartResult);

        CartResult result = cartController.applyCoupons(couponId, cart);

        assertNotNull(result);
        assertEquals(60.0, result.getUpdatedCart().getTotalPrice());
        assertEquals(50.0, result.getUpdatedCart().getFinalPrice());
        verify(applyCouponServiceImpl, times(1)).applyCouponOnCart(couponId, cart);
    }
}
