package com.monk.ecom.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.exception.CouponNotFoundException;
import com.monk.ecom.exception.InvalidInputException;
import com.monk.ecom.service.RetrieveCouponService;
import com.monk.ecom.service.UpdateCouponService;
import com.monk.ecom.service.impl.BxGyCouponServiceImpl;
import com.monk.ecom.service.impl.CartWiseCouponServiceImpl;
import com.monk.ecom.service.impl.ProductWiseCouponServiceImpl;

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

    @InjectMocks
    private CouponController couponController;

    @Mock
    private CartWiseCouponServiceImpl cartWiseCouponService;

    @Mock
    private ProductWiseCouponServiceImpl productWiseCouponService;

    @Mock
    private BxGyCouponServiceImpl bxgyCouponService;

    @Mock
    private RetrieveCouponService retrieveCouponService;

    @Mock
    private UpdateCouponService updateCouponService;

    @Test
    void testCreateCouponCartWise() throws InvalidInputException {
        CreationCriteria criteria = new CreationCriteria();
        criteria.setTypeOfCoupon("cart-wise");

        String response = couponController.createCoupon(criteria);

        assertEquals("cart-wise coupon created Successfully", response);
        verify(cartWiseCouponService, times(1)).createCoupon(criteria);
    }

    @Test
    void testCreateCouponProductWise() throws InvalidInputException {
        CreationCriteria criteria = new CreationCriteria();
        criteria.setTypeOfCoupon("product-wise");

        String response = couponController.createCoupon(criteria);

        assertEquals("product-wise coupon created Successfully", response);
        verify(productWiseCouponService, times(1)).createCoupon(criteria);
    }

    @Test
    void testCreateCouponBxGy() throws InvalidInputException {
        CreationCriteria criteria = new CreationCriteria();
        criteria.setTypeOfCoupon("BxGy");

        String response = couponController.createCoupon(criteria);

        assertEquals("BxGy coupon created Successfully", response);
        verify(bxgyCouponService, times(1)).createCoupon(criteria);
    }

    @Test
    void testCreateCouponInvalidType() {
        CreationCriteria criteria = new CreationCriteria();
        criteria.setTypeOfCoupon("invalid-type");

        InvalidInputException exception = assertThrows(
                InvalidInputException.class,
                () -> couponController.createCoupon(criteria)
        );
        assertEquals("Coupon type invalid-type does not exist", exception.getMessage());
    }

    @Test
    void testFetchAllCouponsSuccess() throws CouponNotFoundException, ConditionNotMetException {
        Coupon c1 = new Coupon("cart-wise", 10.0f, "10 off on 100");
        Coupon c2 = new Coupon("product-wise", 20.0f, "10 off on product P1");
        List<Coupon> mockCoupons = new ArrayList<>();
        mockCoupons.add(c1);
        mockCoupons.add(c2);
        when(retrieveCouponService.fetchAllCoupon()).thenReturn(mockCoupons);

        List<Coupon> result = couponController.fetchCoupon();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(retrieveCouponService, times(1)).fetchAllCoupon();
    }

    @Test
    void testFetchAllCouponsNoCoupons() throws ConditionNotMetException {
        when(retrieveCouponService.fetchAllCoupon()).thenReturn(Collections.emptyList());

        CouponNotFoundException exception = assertThrows(
                CouponNotFoundException.class,
                () -> couponController.fetchCoupon()
        );
        assertEquals("No Coupons Exist", exception.getMessage());
    }

    @Test
    void testFetchCouponByIdSuccess() throws CouponNotFoundException, ConditionNotMetException {
        long id = 1;
        Coupon mockCoupon = new Coupon("cart-wise", 10.0f, "10 off on 100");
        mockCoupon.setId(id);
        when(retrieveCouponService.fetchCoupon(id)).thenReturn(mockCoupon);

        Coupon result = couponController.fetchCoupon(id);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(10.0f, result.getDiscount());
        assertEquals("10 off on 100", result.getDescription());
        verify(retrieveCouponService, times(1)).fetchCoupon(id);
    }

    @Test
    void testUpdateCouponSuccess() throws CouponNotFoundException {
        long id = 1L;
        CreationCriteria criteria = new CreationCriteria();

        String response = couponController.updateCoupon(id, criteria);

        assertEquals("Successfully updated Coupon with couponId: " + id, response);
        verify(updateCouponService, times(1)).updateCoupon(id, criteria);
    }

    @Test
    void testDeleteCouponSuccess() throws CouponNotFoundException {
        long id = 1L;

        String response = couponController.deleteCoupon(id);

        assertEquals("Successfully deleted Coupon with couponId: " + id, response);
        verify(updateCouponService, times(1)).deleteCoupon(id);
    }
}
