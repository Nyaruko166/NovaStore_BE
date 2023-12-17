package com.sd64.novastore.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDiscountHomeResponse {

    private Integer id;

    private String name;

    private BigDecimal priceMax;

    private BigDecimal priceMin;

    private BigDecimal priceDiscountMax;

    private BigDecimal priceDiscountMin;

    private Float value;

    public void comparePrice(BigDecimal price) {
        if (price.compareTo(priceMax) == 1) {
            priceMax = price;
        } else if (price.compareTo(priceMin) == -1) {
            priceMin = price;
        }
    }

    public void comparePriceDiscount(BigDecimal priceDiscount) {
        if (priceDiscount.compareTo(priceDiscountMax) == 1) {
            priceDiscountMax = priceDiscount;
        } else if (priceDiscount.compareTo(priceDiscountMin) == -1) {
            priceDiscountMin = priceDiscount;
        }
    }

    public BigDecimal calculatePriceToPriceDiscountMax() {
        BigDecimal price = new BigDecimal(String.valueOf(priceMax));
        BigDecimal valueConvert = BigDecimal.valueOf(value);
        BigDecimal priceDiscount = price.multiply(valueConvert.divide(new BigDecimal(String.valueOf(priceMax))));
        BigDecimal finalPriceDiscount = price.subtract(priceDiscount);
        System.out.println();
        return finalPriceDiscount;
    }

    public BigDecimal calculatePriceToPriceDiscountMin() {
        BigDecimal price = new BigDecimal(String.valueOf(priceMin));
        BigDecimal valueConvert = BigDecimal.valueOf(value);
        BigDecimal priceDiscount = price.multiply(valueConvert.divide(new BigDecimal(String.valueOf(priceMin))));
        BigDecimal finalPriceDiscount = price.subtract(priceDiscount);
        System.out.println();
        return finalPriceDiscount;
    }
}
