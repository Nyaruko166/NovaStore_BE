package com.sd64.novastore.dto.admin;

import java.math.BigDecimal;
import java.util.Date;

public interface PromotionDetailDTO {
    Integer getPromotionDetailId();
    Integer getProductId();
    String getPromotionName();
    String getProductName();
    String getProductCode();
    BigDecimal getMinProductPrice();
    BigDecimal getMaxProductPrice();
    BigDecimal getMinProductPriceDiscount();
    BigDecimal getMaxProductPriceDiscount();
    Date getPromotionStartDate();
    Date getPromotionEndDate();
    Float getPromotionValue();
}
