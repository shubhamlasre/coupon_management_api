package com.monk.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.monk.ecom.domain.Coupon;

import jakarta.transaction.Transactional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Coupon c SET c.discount = :discount, c.description = :description WHERE c.id = :id")
    int updateCouponDiscountAndDescription(Long id, float discount, String description);
}