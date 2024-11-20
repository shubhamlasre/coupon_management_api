package com.monk.ecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.monk.ecom.domain.ProductCouponMap;

import jakarta.transaction.Transactional;

@Repository
public interface ProductCouponMapRepository extends JpaRepository<ProductCouponMap, Long> {

    public List<ProductCouponMap> findByProductId(long productId);

    @Modifying
    @Transactional
    @Query("UPDATE ProductCouponMap p SET p.totalPrice = :totalPrice, p.discount = :discount, "
            + "p.productId = :productId, p.discountedPrice = :discountedPrice WHERE p.couponId = :couponId")
    public int updateProductCouponMap(@Param("couponId") long couponId,
            @Param("productId") long productId,
            @Param("totalPrice") float totalPrice,
            @Param("discount") float discount,
            @Param("discountedPrice") float discountedPrice);
}
