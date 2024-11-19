package com.monk.ecom.domain.result;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monk.ecom.domain.Coupon;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CouponSearchResult {
    List<Coupon> coupon;
}
