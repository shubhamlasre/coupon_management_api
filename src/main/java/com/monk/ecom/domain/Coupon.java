package com.monk.ecom.domain;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type")
    private String type;

    @Column(name = "discount")
    private float discount;

    @Column(name = "description")
    private String description;

    @Column(name = "applicable_till")
    private LocalDate applicableTill;

    @Column(name = "buy_product_ids")
    private String buyProductIds;

    @Column(name = "buy_product_quantities")
    private String buyProductQuantities;

    @Column(name = "get_product_ids")
    private String getProductIds;

    @Column(name = "get_product_quantities")
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
        LocalDate currentDate = LocalDate.now();
        this.applicableTill = currentDate.plusYears(1);
    }
}