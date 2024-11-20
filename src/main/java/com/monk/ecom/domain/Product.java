package com.monk.ecom.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;

    private String productName;

    private int quantity;

    private float price;

    @Transient
    private float totalDiscount;

    public Product(long productId, float price, int quantity, float totalDiscount) {
        this.price = price;
        this.productId = productId;
        this.quantity = quantity;
        this.totalDiscount = totalDiscount;
    }

}
