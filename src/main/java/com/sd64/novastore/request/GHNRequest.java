/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.request;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GHNRequest {

    private Integer service_type_id;

    private Integer from_district_id;

    private String from_ward_code;

    private Integer to_district_id;

    private String to_ward_code;

    private Integer weight;


}
