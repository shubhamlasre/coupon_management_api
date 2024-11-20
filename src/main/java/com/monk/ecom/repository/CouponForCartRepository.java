package com.monk.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monk.ecom.domain.CouponForCart;

import jakarta.transaction.Transactional;

@Repository
public interface CouponForCartRepository extends JpaRepository<CouponForCart, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CouponForCart c SET c.threshold = :threshold, c.discount = :discount, "
            + "c.description = :description WHERE c.couponId = :couponId")
    int updateCouponForCart(long couponId, float threshold, float discount, String description);
}
