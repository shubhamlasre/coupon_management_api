package com.monk.ecom.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class CreationCriteria {

    @JsonProperty("type")
    private String typeOfCoupon;

    @JsonProperty("details")
    private ProductDetails details;
}
