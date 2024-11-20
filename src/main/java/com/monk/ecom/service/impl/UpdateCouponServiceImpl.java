package com.monk.ecom.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.BuyAndGetCouponMap;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.BuyAndGetCouponRepository;
import com.monk.ecom.repository.CouponForCartRepository;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.repository.ProductCouponMapRepository;
import com.monk.ecom.repository.ProductRepository;
import com.monk.ecom.service.UpdateCouponService;
import com.monk.ecom.util.couponUtility;

@Service
public class UpdateCouponServiceImpl implements UpdateCouponService {

    @Autowired
    private CouponRepository repository;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CouponForCartRepository couponForCartRepo;

    @Autowired
    private ProductCouponMapRepository productCouponMapRepo;

    @Autowired
    private BuyAndGetCouponRepository buyAndGetCouponRepo;

    @Autowired
    private couponUtility utility;

    @Override
    public void updateCoupon(long id, CreationCriteria couponCriteria) {
        Coupon coupon = repository.findById(id).get();
        ProductDetails details = couponCriteria.getDetails();
        if (coupon.getType().equalsIgnoreCase("cart-wise")) {
            String description = utility.getCartWiseDiscountDescription(details);
            repository.updateCouponDiscountAndDescription(id, details.getDiscount(), description);

            couponForCartRepo.updateCouponForCart(id, details.getThreshold(), details.getDiscount(),
                    description);
        } else if (coupon.getType().equalsIgnoreCase("product-wise")) {
            String description = utility.getProductWiseDiscountDescription(details);
            repository.updateCouponDiscountAndDescription(id, details.getDiscount(), description);
            Product product = productRepo.findById(details.getProductId()).get();
            productCouponMapRepo.updateProductCouponMap(id, details.getProductId(),
                    product.getPrice(), details.getDiscount(), product.getPrice() - details.getDiscount());
        } else if (coupon.getType().equalsIgnoreCase("BxGy")) {
            String description = utility.getBxGyDiscountDescription(details);
            repository.updateCouponDiscountAndDescription(id, details.getDiscount(), description);
            buyAndGetCouponRepo.deleteByCouponId(id);
            List<BuyAndGetCouponMap> entiityUpdateList = new ArrayList<>();
            for (Product buyProd : details.getBuyProducts()) {
                for (Product getProd : details.getGetProducts()) {
                    Product product = productRepo.findById(getProd.getProductId()).get();
                    float discount = product.getPrice() * getProd.getQuantity();
                    BuyAndGetCouponMap bngCouponMap = new BuyAndGetCouponMap(id, buyProd.getProductId(),
                            buyProd.getQuantity(), getProd.getProductId(), getProd.getQuantity(), discount);
                    entiityUpdateList.add(bngCouponMap);
                }
            }
            buyAndGetCouponRepo.saveAll(entiityUpdateList);
        }
    }

    @Override
    public void deleteCoupon(long id) {
        Optional<Coupon> coupon = repository.findById(id);
        if (coupon.isPresent()) {
            repository.deleteById(id);
            couponForCartRepo.deleteById(id);
        } else {
            // coupon not present error;
        }
    }

}
