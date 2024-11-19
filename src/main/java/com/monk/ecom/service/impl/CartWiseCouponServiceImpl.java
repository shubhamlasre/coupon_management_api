package com.monk.ecom.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.domain.Coupon;
import com.monk.ecom.domain.CreationCriteria;
import com.monk.ecom.domain.Product;
import com.monk.ecom.repository.CouponRepository;
import com.monk.ecom.service.CouponService;

@Service
public class CartWiseCouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepo;

    @Override
    public Coupon createCoupon(CreationCriteria couponCreationCriteria) {
        String type = couponCreationCriteria.getTypeOfCoupon();
        float discount = 0;
        String description = null;
        ProductDetails couponDetails = couponCreationCriteria.getDetails();
        Coupon couponResult = null;
        if (type.equalsIgnoreCase("cart-wise")) {
            discount = couponDetails.getDiscount();
            description = getCartWiseDiscountDescription(couponDetails);
            couponResult = new Coupon(couponCreationCriteria.getTypeOfCoupon(), discount, description,
                    null, null, null, null);
        } else if (type.equalsIgnoreCase("cart-wise")) {
            discount = couponDetails.getDiscount();
            description = getProductWiseDiscountDescription(couponDetails);
            couponResult = new Coupon(couponCreationCriteria.getTypeOfCoupon(), discount, description,
                    fetchBuyProductId(couponDetails.getBuyProducts()),
                    fetchBuyProductQuantity(couponDetails.getBuyProducts()),
                    fetchGetProductId(couponDetails.getGetProducts()),
                    fetchGetProductQuantity(couponDetails.getGetProducts()));
        }

        return couponResult;
    }

    private String getCartWiseDiscountDescription(ProductDetails details) {
        StringBuilder description = new StringBuilder();
        description.append(details.getDiscount());
        description.append(": ");
        float discountPercent = (details.getDiscount() * 100) / details.getPrice();
        description.append(discountPercent);
        description.append("% of ");
        description.append(details.getPrice());
        return description.toString();
    }

    private String getProductWiseDiscountDescription(ProductDetails details) {
        StringBuilder description = new StringBuilder();
        description.append(details.getDiscount());
        description.append(": ");
        // get price by calling db
        float discountPercent = (details.getDiscount() * 100) / details.getPrice();
        description.append(discountPercent);
        description.append("flat ");
        description.append(details.getPrice());
        return description.toString();
    }

    private String fetchBuyProductId(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getPrice());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

    private String fetchBuyProductQuantity(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getQuantity());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

    private String fetchGetProductId(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getPrice());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

    private String fetchGetProductQuantity(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getQuantity());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

}