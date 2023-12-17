package com.sd64.novastore.dto.admin;

import java.math.BigDecimal;
import java.util.Date;

public interface PromotionDetailDTO {
    Integer getPromotionDetailId();
    String getPromotionName();
    String getProductName();
    String getProductCode();
    BigDecimal getMinProductPrice();
    BigDecimal getMaxProductPrice();
    Date getPromotionStartDate();
    Date getPromotionEndDate();
    Float getPromotionValue();
}
