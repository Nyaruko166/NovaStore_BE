package com.sd64.novastore.dto.Impl;

import com.sd64.novastore.dto.ProductDetailDto;
import com.sd64.novastore.response.ProductDetailSearchResponse;

public class ProductDetailDtoImpl {
    public static ProductDetailSearchResponse toProductSearchResponse(ProductDetailDto dto) {
        return ProductDetailSearchResponse.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .sizeName(dto.getSizeName())
                .colorName(dto.getColorName())
                .quantity(dto.getQuantity())
                .build();
    }
}
