package com.monk.ecom.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.exception.ConditionNotMetException;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.service.RetrieveCouponService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class RetrieveCouponServiceImpl implements RetrieveCouponService {

    @Autowired
    private CouponRepository repository;

    @Override
    public List<Coupon> fetchAllCoupon() throws ConditionNotMetException {
        List<Coupon> coupons = new ArrayList<>();
        try {
            coupons = repository.findAll();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ConditionNotMetException("Exception while fetching Coupons");
        }
        return coupons;
    }

    @Override
    public Coupon fetchCoupon(long id) throws ConditionNotMetException {
        Coupon coupons = new Coupon();
        try {
            coupons = repository.findById(id).get();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ConditionNotMetException("Exception while fetching Coupon with couponId: " + id);
        }
        return coupons;
    }

}
