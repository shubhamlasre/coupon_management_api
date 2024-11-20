package com.monk.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.monk.ecom.domain.BuyAndGetCouponMap;

import jakarta.transaction.Transactional;

@Repository
public interface BuyAndGetCouponRepository extends JpaRepository<BuyAndGetCouponMap, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE BuyAndGetCouponMap b SET b.buyProductId = :buyProductId, b.buyProductQuantity = :buyProductQuantity, "
            + "b.availProductId = :availProductId, b.availProductQuantity = :availProductQuantity, b.discount = :discount "
            + "WHERE b.couponId = :couponId")
    int updateBuyAndGetCouponMap(@Param("couponId") long couponId,
            @Param("buyProductId") long buyProductId,
            @Param("buyProductQuantity") int buyProductQuantity,
            @Param("availProductId") long availProductId,
            @Param("availProductQuantity") int availProductQuantity,
            @Param("discount") float discount);

    @Modifying
    @Transactional
    @Query("DELETE FROM BuyAndGetCouponMap b WHERE b.couponId = :couponId")
    void deleteByCouponId(long couponId);
}
