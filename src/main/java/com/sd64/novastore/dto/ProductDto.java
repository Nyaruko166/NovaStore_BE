package com.sd64.novastore.dto;

import java.math.BigDecimal;

public interface ProductDto {

    Integer getId();

    String getName();

    String getDescription();

    BigDecimal getPrice();

    String getCategoryName();

    String getBrandName();

    String getMaterialName();

    String getFormName();

}
