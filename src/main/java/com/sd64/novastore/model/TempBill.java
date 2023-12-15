/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class TempBill {

    private Integer billId;

    private String idCustomer;

    private String customerName;

    private String customerPhone;

    private String customerEmail;

    private List<OfflineCart> lstDetailProduct;

    public Integer billIdPP() {
        return billId + 1;
    }

}
