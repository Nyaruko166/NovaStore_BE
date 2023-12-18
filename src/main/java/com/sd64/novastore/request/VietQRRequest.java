/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

/*
 *  © 2023 Nyaruko166
 */

package com.sd64.novastore.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VietQRRequest {
    private String accountNo;
    private String accountName;
    private String acqId;
    private Long amount;
    private String addInfo;
    private String format;
    private String template;
}
