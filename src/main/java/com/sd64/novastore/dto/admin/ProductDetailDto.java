package com.sd64.novastore.dto.admin;

import java.math.BigDecimal;

public interface ProductDetailDto {

    Integer getId();

    String getCode();

    Integer getQuantity();

    BigDecimal getPrice();

    BigDecimal getPriceDiscount();

    String getSizeName();

    String getColorName();

}
