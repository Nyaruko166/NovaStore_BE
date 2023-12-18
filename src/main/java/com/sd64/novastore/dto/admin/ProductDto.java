package com.sd64.novastore.dto.admin;

import java.math.BigDecimal;

public interface ProductDto {

    Integer getId();

    String getCode();

    String getName();

    String getDescription();

    BigDecimal getPrice();

    String getCategoryName();

    String getBrandName();

    String getMaterialName();

    String getFormName();

    Integer getImageId();

    Integer getStatus();

}
