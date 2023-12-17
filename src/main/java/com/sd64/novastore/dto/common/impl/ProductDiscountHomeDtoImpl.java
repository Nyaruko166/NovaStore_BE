package com.sd64.novastore.dto.common.impl;

import com.sd64.novastore.dto.common.ProductDiscountHomeDto;
import com.sd64.novastore.dto.common.ProductHomeDto;
import com.sd64.novastore.response.ProductDiscountHomeResponse;
import com.sd64.novastore.response.ProductHomeResponse;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDiscountHomeDtoImpl {

    Integer productId;

    String productName;

    BigDecimal price;

    BigDecimal priceDiscount;

    Float value;

    public static ProductDiscountHomeDtoImpl toData(ProductDiscountHomeDto dto) {
        ProductDiscountHomeDtoImpl productDiscountHomeResponseDto = new ProductDiscountHomeDtoImpl();
        productDiscountHomeResponseDto.productName = dto.getProductName();
        productDiscountHomeResponseDto.productId = dto.getProductId();
        productDiscountHomeResponseDto.price = dto.getPrice();
        productDiscountHomeResponseDto.priceDiscount = dto.getPriceDiscount();
        productDiscountHomeResponseDto.value = dto.getValue();
        return productDiscountHomeResponseDto;
    }

    public ProductDiscountHomeResponse toResponse() {
        ProductDiscountHomeResponse response = new ProductDiscountHomeResponse();
        response.setId(this.productId);
        response.setName(this.productName);
        response.setPriceMax(this.price);
        response.setPriceMin(this.price);
        response.setPriceDiscountMax(this.priceDiscount);
        response.setPriceDiscountMin(this.priceDiscount);
        response.setValue(this.value);
        return response;
    }
}
