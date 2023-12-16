/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;
}
