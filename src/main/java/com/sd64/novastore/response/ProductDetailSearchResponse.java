package com.sd64.novastore.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailSearchResponse {
    private Integer id;
    private Integer quantity;
    private String sizeName;
    private String colorName;
}
