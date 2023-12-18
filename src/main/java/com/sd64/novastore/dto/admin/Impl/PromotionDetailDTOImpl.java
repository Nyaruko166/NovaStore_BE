//package com.sd64.novastore.dto.admin.Impl;
//
//import com.sd64.novastore.dto.admin.PromotionDetailDTO;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//public class PromotionDetailDTOImpl implements PromotionDetailDTO {
//    private Integer promotionDetailId;
//    private String promotionName;
//    private String productName;
//    private String productCode;
//    private BigDecimal minProductPrice;
//    private BigDecimal maxProductPrice;
//    private Date promotionStartDate;
//    private Date promotionEndDate;
//    private Float promotionValue;
//
//    public PromotionDetailDTOImpl(Integer promotionDetailId, String promotionName, String productName, String productCode,
//                                  BigDecimal minProductPrice, BigDecimal maxProductPrice, Date promotionStartDate,
//                                  Date promotionEndDate, Float promotionValue) {
//        this.promotionDetailId = promotionDetailId;
//        this.promotionName = promotionName;
//        this.productName = productName;
//        this.productCode = productCode;
//        this.minProductPrice = minProductPrice;
//        this.maxProductPrice = maxProductPrice;
//        this.promotionStartDate = promotionStartDate;
//        this.promotionEndDate = promotionEndDate;
//        this.promotionValue = promotionValue;
//    }
//
//    @Override
//    public Integer getPromotionDetailId() {
//        return promotionDetailId;
//    }
//
//    @Override
//    public String getPromotionName() {
//        return promotionName;
//    }
//
//    @Override
//    public String getProductName() {
//        return productName;
//    }
//
//    @Override
//    public String getProductCode() {
//        return productCode;
//    }
//
//    @Override
//    public BigDecimal getMinProductPrice() {
//        return minProductPrice;
//    }
//
//    @Override
//    public BigDecimal getMaxProductPrice() {
//        return maxProductPrice;
//    }
//
//    @Override
//    public BigDecimal getMinProductPriceDiscount() {
//        return null;
//    }
//
//    @Override
//    public BigDecimal getMaxProductPriceDiscount() {
//        return null;
//    }
//
//    @Override
//    public Date getPromotionStartDate() {
//        return promotionStartDate;
//    }
//
//    @Override
//    public Date getPromotionEndDate() {
//        return promotionEndDate;
//    }
//
//    @Override
//    public Float getPromotionValue() {
//        return promotionValue;
//    }
//}
