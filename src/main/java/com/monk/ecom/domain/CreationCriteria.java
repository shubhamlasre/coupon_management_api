package com.monk.ecom.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Getter
public class CreationCriteria {

    @JsonProperty("type")
    private String typeOfCoupon;

    @JsonProperty("details")
    private ProductDetails details;
}
