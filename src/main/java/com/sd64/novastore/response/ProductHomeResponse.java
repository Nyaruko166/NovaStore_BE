package com.sd64.novastore.response;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductHomeResponse {

    private Integer id;

    private String name;

    private BigDecimal priceMax;

    private BigDecimal priceMin;

    public void comparePrice(BigDecimal price) {
        if (price.compareTo(priceMax) == 1) {
            priceMax = price;
        } else if (price.compareTo(priceMin) == -1) {
            priceMin = price;
        }
    }
}
