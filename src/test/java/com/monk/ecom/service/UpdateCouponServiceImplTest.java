package com.monk.ecom.service;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.exception.CouponNotFoundException;
import com.monk.ecom.repository.BuyAndGetCouponRepository;
import com.monk.ecom.repository.CouponForCartRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductCouponMapRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.impl.UpdateCouponServiceImpl;
import com.monk.ecom.util.couponUtility;

@ExtendWith(MockitoExtension.class)
class UpdateCouponServiceImplTest {

    @InjectMocks
    private UpdateCouponServiceImpl updateCouponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private ProductRepository productRepo;

    @Mock
    private CouponForCartRepository couponForCartRepo;

    @Mock
    private ProductCouponMapRepository productCouponMapRepo;

    @Mock
    private BuyAndGetCouponRepository buyAndGetCouponRepo;

    @Mock
    private couponUtility utility;

    @Test
    void testUpdateCoupon_CartWise_Success() throws CouponNotFoundException {
        long couponId = 1L;
        Coupon mockCoupon = new Coupon("cart-wise", 10.0f, "10 off on 100");
        ProductDetails mockDetails = new ProductDetails(50.0f, 5.0f, 1L, new ArrayList<>(), new ArrayList<>(), 2);
        CreationCriteria criteria = new CreationCriteria("cart-wise", mockDetails);
        when(couponRepository.findById(couponId)).thenReturn(Optional.of(mockCoupon));
        when(utility.getCartWiseDiscountDescription(mockDetails)).thenReturn("Updated Description");

        updateCouponService.updateCoupon(couponId, criteria);

        verify(couponRepository, times(1))
                .updateCouponDiscountAndDescription(couponId, mockDetails.getDiscount(), "Updated Description");
        verify(couponForCartRepo, times(1))
                .updateCouponForCart(couponId, mockDetails.getThreshold(), mockDetails.getDiscount(),
                        "Updated Description");
    }

    @Test
    void testUpdateCoupon_ProductWise_Success() throws CouponNotFoundException {
        long couponId = 2L;
        Coupon mockCoupon = new Coupon("product-wise", 10.0f, "10 off on 100");
        ProductDetails mockDetails = new ProductDetails(50.0f, 5.0f, 1L, new ArrayList<>(), new ArrayList<>(), 2);
        CreationCriteria criteria = new CreationCriteria("product-wise", mockDetails);
        Product mockProduct = new Product(1l, 100f, 5, 0);

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(mockCoupon));
        when(productRepo.findById(mockDetails.getProductId())).thenReturn(Optional.of(mockProduct));
        when(utility.getProductWiseDiscountDescription(mockDetails)).thenReturn("Updated Product Description");

        updateCouponService.updateCoupon(couponId, criteria);

        verify(couponRepository, times(1))
                .updateCouponDiscountAndDescription(couponId, mockDetails.getDiscount(), "Updated Product Description");
        verify(productCouponMapRepo, times(1))
                .updateProductCouponMap(couponId, mockDetails.getProductId(), mockProduct.getPrice(),
                        mockDetails.getDiscount(), mockProduct.getPrice() - mockDetails.getDiscount());
    }

    @Test
    void testUpdateCoupon_NotFound() {
        long couponId = 3L;
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CouponNotFoundException.class, () -> {
            updateCouponService.updateCoupon(couponId, new CreationCriteria());
        });

        assertEquals("Coupon with couponId: 3 does not exist", exception.getMessage());
    }

    @Test
    void testDeleteCoupon_Success() throws CouponNotFoundException {
        long couponId = 4L;
        Coupon mockCoupon = new Coupon("cart-wise", 10.0f, "10 off on 100");

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(mockCoupon));

        updateCouponService.deleteCoupon(couponId);

        verify(couponForCartRepo, times(1)).deleteById(couponId);
        verify(couponRepository, times(1)).deleteById(couponId);
    }

    @Test
    void testDeleteCoupon_NotFound() {
        long couponId = 5L;
        when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(CouponNotFoundException.class, () -> {
            updateCouponService.deleteCoupon(couponId);
        });

        assertEquals("Coupon with couponId: 5 does not exist", exception.getMessage());
    }
}
