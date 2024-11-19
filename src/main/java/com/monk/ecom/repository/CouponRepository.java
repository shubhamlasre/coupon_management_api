package com.monk.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.monk.ecom.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}