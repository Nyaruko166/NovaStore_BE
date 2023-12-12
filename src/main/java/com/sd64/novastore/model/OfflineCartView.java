/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.model;

import lombok.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OfflineCartView {

    private Integer idCtsp;

    private String name;

    private String codeCtsp;

    private Integer qty;

    private BigDecimal price;

//    private BigDecimal totalPrice;

    private String color;

    private String size;

    public String calTotalPrice() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        return decimalFormat.format(this.price.multiply(BigDecimal.valueOf(this.qty)));
    }

    public String fieldCombine() {
        return String.join(", ", size, color);
    }

}
