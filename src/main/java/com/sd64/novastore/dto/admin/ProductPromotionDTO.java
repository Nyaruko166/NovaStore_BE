package com.sd64.novastore.dto.admin;

import java.math.BigDecimal;

public interface ProductPromotionDTO {
    Integer getId();
    String getCode();
    String getName();
    BigDecimal getMinProductPrice();
    BigDecimal getMaxProductPrice();
}
