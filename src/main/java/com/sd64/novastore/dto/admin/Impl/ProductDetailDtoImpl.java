package com.sd64.novastore.dto.admin.Impl;

import com.sd64.novastore.dto.admin.ProductDetailDto;
import com.sd64.novastore.response.ProductDetailSearchResponse;

public class ProductDetailDtoImpl {
    public static ProductDetailSearchResponse toProductSearchResponse(ProductDetailDto dto) {
        return ProductDetailSearchResponse.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .price(dto.getPrice())
                .sizeName(dto.getSizeName())
                .colorName(dto.getColorName())
                .quantity(dto.getQuantity())
                .build();
    }
}
