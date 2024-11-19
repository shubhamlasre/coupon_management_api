package com.monk.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.monk.ecom.domain.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

}