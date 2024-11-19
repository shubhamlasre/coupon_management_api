package com.monk.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.Coupon;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.service.RetrieveCouponService;

@Service
public class RetrieveCouponServiceImpl implements RetrieveCouponService {

    @Autowired
    private CouponRepository repository;

    @Override
    public List<Coupon> fetchAllCoupon() {
        List<Coupon> coupons = repository.findAll();
        return coupons;
    }

    @Override
    public List<Coupon> fetchCoupon(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
