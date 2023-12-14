/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OfflineCart {

    private String detailProductId;

    private Integer qty;

}
