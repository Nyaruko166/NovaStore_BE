package com.sd64.novastore.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PropertiesResponse {
    private String code;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal priceDiscount;
    private Integer id;
}

