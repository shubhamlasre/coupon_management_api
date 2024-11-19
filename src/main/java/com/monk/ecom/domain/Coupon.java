package com.monk.ecom.domain;

import java.util.Date;

public class Coupon {

    private long id;
    private String type;
    private float discount;
    private String description;
    private Date applicableTill;
    private String buyProductIds;
    private String buyProductQuantities;
    private String getProductIds;
    private String getProductQuantities;

    public Coupon(String type, float discount, String description, String buyProductIds,
            String buyProductQuantities, String getProductIds, String getProductQuantities) {
        this.type = type;
        this.discount = discount;
        this.description = description;
        this.buyProductIds = buyProductIds;
        this.buyProductQuantities = buyProductQuantities;
        this.getProductIds = getProductIds;
        this.getProductQuantities = getProductQuantities;
        Date currentDate = new Date();
        this.applicableTill = new Date(currentDate.getYear() + 1, currentDate.getMonth(), currentDate.getDate());
    }
}