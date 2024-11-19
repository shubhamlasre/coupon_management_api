package com.monk.ecom.util;

import java.util.List;

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
        float discountPercent = (details.getDiscount() * 100) / details.getPrice();
        description.append(discountPercent);
        description.append("% of ");
        description.append(details.getPrice());
        return description.toString();
    }

    public String getProductWiseDiscountDescription(ProductDetails details) {
        StringBuilder description = new StringBuilder();
        description.append(details.getDiscount());
        description.append(": ");
        Product product = productRepo.findById(details.getProductId()).get();
        description.append(details.getDiscount());
        description.append(" RS discount on selected product");
        description.append(product.getProductName());
        return description.toString();
    }

    public String getBxGyDiscountDescription(ProductDetails details) {
        StringBuilder description = new StringBuilder();
        description.append("Buy ");
        for (Product p : details.getBuyProducts()) {
            description.append(p.getQuantity());
            description.append(" product of ");
            description.append(p.getProductName());
            description.append(" OR");
        }
        description.substring(0, description.lastIndexOf("OR"));
        description.append("and get");
        for (Product p : details.getBuyProducts()) {
            description.append(p.getQuantity());
            description.append(" product of ");
            description.append(p.getProductName());
            description.append(" OR");
        }
        description.substring(0, description.lastIndexOf("OR"));
        return description.toString();
    }

    public String fetchBuyProductId(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getPrice());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

    public String fetchBuyProductQuantity(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getQuantity());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

    public String fetchGetProductId(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getPrice());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }

    public String fetchGetProductQuantity(List<Product> products) {
        StringBuilder productIds = new StringBuilder();
        for (Product prod : products) {
            productIds.append(prod.getQuantity());
            productIds.append(",");
        }
        return productIds.substring(0, productIds.length() - 1);
    }
}
