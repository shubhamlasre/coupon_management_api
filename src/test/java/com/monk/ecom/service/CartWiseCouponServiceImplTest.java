package com.monk.ecom.service;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CouponForCart;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.CouponForCartRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.service.impl.CartWiseCouponServiceImpl;
import com.monk.ecom.util.couponUtility;

@ExtendWith(MockitoExtension.class)
class CartWiseCouponServiceImplTest {

    @InjectMocks
    private CartWiseCouponServiceImpl cartWiseCouponService;

    @Mock
    private CouponRepository couponRepo;

    @Mock
    private CouponForCartRepository couponForCartRepo;

    @Mock
    private couponUtility utility;

    @Test
    void testCreateCoupon() {
        CreationCriteria mockCriteria = new CreationCriteria();
        ProductDetails mockDetails = new ProductDetails(100.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), 2);
        mockDetails.setDiscount(20.0f);
        mockDetails.setThreshold(100.0f);

        mockCriteria.setTypeOfCoupon("cart-wise");
        mockCriteria.setDetails(mockDetails);

        String mockDescription = "20: 20% of 100";
        Coupon mockSavedCoupon = new Coupon("cart-wise", 20.0f, mockDescription);
        mockSavedCoupon.setId(1L);

        when(utility.getCartWiseDiscountDescription(mockDetails)).thenReturn(mockDescription);
        when(couponRepo.save(any(Coupon.class))).thenReturn(mockSavedCoupon);

        Coupon result = cartWiseCouponService.createCoupon(mockCriteria);

        assertNotNull(result);
        assertEquals("cart-wise", result.getType());
        assertEquals(20.0f, result.getDiscount());
        assertEquals(mockDescription, result.getDescription());

        verify(utility, times(1)).getCartWiseDiscountDescription(mockDetails);
        verify(utility, times(1)).getExpiryDate();
        verify(couponRepo, times(1)).save(any(Coupon.class));
        verify(couponForCartRepo, times(1)).save(any(CouponForCart.class));
    }
}
