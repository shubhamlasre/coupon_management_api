package com.monk.ecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.monk.ecom.domain.BuyAndGetCouponMap;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.BuyAndGetCouponRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.impl.BxGyCouponServiceImpl;
import com.monk.ecom.util.couponUtility;

@ExtendWith(MockitoExtension.class)
class BxGyCouponServiceImplTest {

    @InjectMocks
    private BxGyCouponServiceImpl bxGyCouponService;

    @Mock
    private CouponRepository couponRepo;

    @Mock
    private ProductRepository productRepo;

    @Mock
    private BuyAndGetCouponRepository buyAndGetCouponRepo;

    @Mock
    private couponUtility utility;

    @Test
    void testCreateCouponSuccess() {
        ProductDetails mockDetails = new ProductDetails(0.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), 5);

        Product buyProduct = new Product(1l, 200f, 5, 0);
        Product getProduct = new Product(2l, 300f, 5, 0);

        mockDetails.setBuyProducts(List.of(buyProduct));
        mockDetails.setGetProducts(List.of(getProduct));

        CreationCriteria mockCriteria = new CreationCriteria();
        mockCriteria.setTypeOfCoupon("BxGy");
        mockCriteria.setDetails(mockDetails);

        String mockDescription = "Buy 2 of Product 1, get 1 of Product 2.";
        Coupon mockSavedCoupon = new Coupon("BxGy", 20.0f, mockDescription);
        mockSavedCoupon.setId(1L);

        Product mockGetProductFromRepo = new Product(2l, 500f, 1, 0);

        when(utility.getBxGyDiscountDescription(mockDetails)).thenReturn(mockDescription);
        when(couponRepo.save(any(Coupon.class))).thenReturn(mockSavedCoupon);
        when(productRepo.findById(2L)).thenReturn(Optional.of(mockGetProductFromRepo));

        Coupon result = bxGyCouponService.createCoupon(mockCriteria);

        assertNotNull(result);
        assertEquals("BxGy", result.getType());
        assertEquals(20.0f, result.getDiscount());
        assertEquals(mockDescription, result.getDescription());

        verify(couponRepo, times(1)).save(any(Coupon.class));
        verify(productRepo, times(1)).findById(2L);
        verify(buyAndGetCouponRepo, times(1)).save(any(BuyAndGetCouponMap.class));
    }

    @Test
    void testCreateCouponProductNotFound() {
        ProductDetails mockDetails = new ProductDetails(0.0f, 20.0f, 1L, new ArrayList<>(), new ArrayList<>(), 5);

        Product buyProduct = new Product(1l, 200f, 5, 0);
        Product getProduct = new Product(10l, 300f, 5, 0);

        mockDetails.setBuyProducts(List.of(buyProduct));
        mockDetails.setGetProducts(List.of(getProduct));

        CreationCriteria mockCriteria = new CreationCriteria();
        mockCriteria.setTypeOfCoupon("BxGy");
        mockCriteria.setDetails(mockDetails);

        when(productRepo.findById(10L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bxGyCouponService.createCoupon(mockCriteria);
        });

        assertEquals("No value present", exception.getMessage());
        verify(productRepo, times(1)).findById(10L);
    }
}
