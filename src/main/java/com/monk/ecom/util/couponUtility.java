package com.monk.ecom.util;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.monk.ecom.domain.Product;
import com.monk.ecom.domain.ProductDetails;
import com.monk.ecom.repository.ProductRepository;

@Component
public class couponUtility {

    @Autowired
    private ProductRepository productRepo;

    public String getCartWiseDiscountDescription(ProductDetails details) {
        StringBuilder description = new StringBuilder();
        description.append(details.getDiscount());
        description.append(": ");
        float discountPercent = (details.getDiscount() * 100) / details.getThreshold();
        description.append(discountPercent);
        description.append("% of ");
        description.append(details.getThreshold());
        return description.toString();
    }

    public String getProductWiseDiscountDescription(ProductDetails details) {
        StringBuilder description = new StringBuilder();
        description.append(details.getDiscount());
        description.append(": ");
        Product product = productRepo.findById(details.getProductId()).get();
        description.append(details.getDiscount());
        description.append(" RS discount on selected product ");
        description.append(product.getProductName());
        return description.toString();
    }

    public String getBxGyDiscountDescription(ProductDetails details) {
        StringBuilder buyDesc = new StringBuilder();
        buyDesc.append("Buy ");
        for (Product p : details.getBuyProducts()) {
            buyDesc.append(p.getQuantity());
            buyDesc.append(" product of ");
            Product product = productRepo.findById(p.getProductId()).get();
            String productName = product.getProductName();
            buyDesc.append(productName);
            buyDesc.append(" OR ");
        }
        String buyDescription = buyDesc.toString().substring(0, buyDesc.length() - 3);
        StringBuilder getDesc = new StringBuilder("and get ");
        for (Product p : details.getGetProducts()) {
            getDesc.append(p.getQuantity());
            getDesc.append(" product of ");
            Product product = productRepo.findById(p.getProductId()).get();
            String productName = product.getProductName();
            getDesc.append(productName);
            getDesc.append(" OR ");
        }
        String getDescription = getDesc.toString().substring(0, getDesc.length() - 3);
        return buyDescription + getDescription;
    }

    public LocalDate getExpiryDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusYears(1);
    }

}
