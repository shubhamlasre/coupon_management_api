package com.monk.ecom.service;

import java.util.ArrayList;
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

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductCouponMap;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductCouponMapRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.impl.ProductWiseCouponServiceImpl;
import com.monk.ecom.util.couponUtility;

@ExtendWith(MockitoExtension.class)
class ProductWiseCouponServiceImplTest {

    @InjectMocks
    private ProductWiseCouponServiceImpl productWiseCouponService;

    @Mock
    private CouponRepository couponRepo;

    @Mock
    private ProductRepository productRepo;

    @Mock
    private ProductCouponMapRepository productCouponMapRepo;

    @Mock
    private couponUtility utility;

    @Test
    void testCreateCoupon() {
        ProductDetails mockDetails = new ProductDetails(0.0f, 50.0f, 1L, new ArrayList<>(), new ArrayList<>(), 2);

        CreationCriteria mockCriteria = new CreationCriteria();
        mockCriteria.setTypeOfCoupon("product-wise");
        mockCriteria.setDetails(mockDetails);

        Product mockProduct = new Product(1l, 500f, 5, 50);

        String mockDescription = "50.0 off on specific product P1";
        Coupon mockSavedCoupon = new Coupon("product-wise", 50.0f, mockDescription);
        mockSavedCoupon.setId(1L);

        when(productRepo.findById(1L)).thenReturn(Optional.of(mockProduct));
        when(utility.getProductWiseDiscountDescription(mockDetails)).thenReturn(mockDescription);
        when(couponRepo.save(any(Coupon.class))).thenReturn(mockSavedCoupon);

        Coupon result = productWiseCouponService.createCoupon(mockCriteria);

        assertNotNull(result);
        assertEquals("product-wise", result.getType());
        assertEquals(50.0f, result.getDiscount());
        assertEquals(mockDescription, result.getDescription());

        verify(productRepo, times(1)).findById(1L);
        verify(couponRepo, times(1)).save(any(Coupon.class));
        verify(productCouponMapRepo, times(1)).save(any(ProductCouponMap.class));
        verify(utility, times(1)).getProductWiseDiscountDescription(mockDetails);
        verify(utility, times(1)).getExpiryDate();
    }

    @Test
    void testCreateCouponProductNotFound() {
        ProductDetails mockDetails = new ProductDetails(0.0f, 50.0f, 10L, new ArrayList<>(), new ArrayList<>(), 2);

        CreationCriteria mockCriteria = new CreationCriteria();
        mockCriteria.setTypeOfCoupon("product-wise");
        mockCriteria.setDetails(mockDetails);

        when(productRepo.findById(10L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            productWiseCouponService.createCoupon(mockCriteria);
        });

        assertEquals("No value present", exception.getMessage());
        verify(productRepo, times(1)).findById(10L);
    }
}
