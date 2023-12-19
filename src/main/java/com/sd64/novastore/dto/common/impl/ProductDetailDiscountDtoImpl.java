package com.sd64.novastore.dto.common.impl;

import com.sd64.novastore.dto.common.ProductDetailAndValueDiscountDto;
import lombok.Data;

@Data
public class ProductDetailDiscountDtoImpl {
    Integer id;

    Float value;

    Boolean isDiscount;

    public static ProductDetailDiscountDtoImpl toResponse(ProductDetailAndValueDiscountDto dto) {

        ProductDetailDiscountDtoImpl response = new ProductDetailDiscountDtoImpl();
        if (dto == null) {
            response.setValue(new Float(0));
            response.setIsDiscount(false);
        } else {
            response.setId(dto.getProductDetailId());
            response.setValue(dto.getValue());
            if (dto.getValue() == null) {
                response.setIsDiscount(false);
                response.setValue(new Float(0));
            } else {
                response.setIsDiscount(true);
            }
        }
        return response;
    }
}
