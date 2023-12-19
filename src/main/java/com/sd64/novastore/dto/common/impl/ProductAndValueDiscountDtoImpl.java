package com.sd64.novastore.dto.common.impl;

import com.sd64.novastore.dto.common.ProductAndValueDiscountDto;
import com.sd64.novastore.dto.common.ProductDetailAndValueDiscountDto;
import lombok.Data;

@Data
public class ProductAndValueDiscountDtoImpl {

    Integer id;

    Float value;

    Boolean isDiscount;

    public static ProductAndValueDiscountDtoImpl toResponse(ProductAndValueDiscountDto dto) {

        ProductAndValueDiscountDtoImpl response = new ProductAndValueDiscountDtoImpl();
        if (dto == null) {
            response.setValue((float) 0);
            response.setIsDiscount(false);
        } else {
            response.setId(dto.getProductId());
            response.setValue(dto.getValue());
            if (dto.getValue() == null) {
                response.setIsDiscount(false);
                response.setValue((float) 0);
            } else {
                response.setIsDiscount(true);
            }
        }
        return response;
    }
}
