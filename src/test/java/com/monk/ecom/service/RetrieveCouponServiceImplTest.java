package com.monk.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.service.impl.RetrieveCouponServiceImpl;

@ExtendWith(MockitoExtension.class)
class RetrieveCouponServiceImplTest {

    @InjectMocks
    private RetrieveCouponServiceImpl retrieveCouponService;

    @Mock
    private CouponRepository repository;

    @Test
    void testFetchAllCoupon_Success() throws ConditionNotMetException {
        Coupon c1 = new Coupon("cart-wise", 10.0f, "10 off on 100");
        Coupon c2 = new Coupon("product-wise", 20.0f, "10 off on product P1");
        List<Coupon> mockCoupons = new ArrayList<>();
        mockCoupons.add(c1);
        mockCoupons.add(c2);

        when(repository.findAll()).thenReturn(mockCoupons);

        List<Coupon> result = retrieveCouponService.fetchAllCoupon();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("cart-wise", result.get(0).getType());
        assertEquals(10.0f, result.get(0).getDiscount());
        assertEquals("product-wise", result.get(1).getType());
        assertEquals(20.0f, result.get(1).getDiscount());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFetchAllCouponException() {
        when(repository.findAll()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(ConditionNotMetException.class, () -> {
            retrieveCouponService.fetchAllCoupon();
        });

        assertEquals("Exception while fetching Coupons", exception.getMessage());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFetchCouponSuccess() throws ConditionNotMetException {
        Coupon mockCoupon = new Coupon("cart-wise", 10.0f, "10 off on 100");
        mockCoupon.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(mockCoupon));

        Coupon result = retrieveCouponService.fetchCoupon(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("cart-wise", result.getType());
        assertEquals(10.0f, result.getDiscount());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFetchCouponNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ConditionNotMetException.class, () -> {
            retrieveCouponService.fetchCoupon(1L);
        });

        assertEquals("Exception while fetching Coupon with couponId: 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFetchCouponException() {
        when(repository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(ConditionNotMetException.class, () -> {
            retrieveCouponService.fetchCoupon(1L);
        });

        assertEquals("Exception while fetching Coupon with couponId: 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }
}
