package com.sd64.novastore.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductDetailResponse {

    private Integer productId;

    private String productName;

    private BigDecimal price;

    private Integer quantity;
}
