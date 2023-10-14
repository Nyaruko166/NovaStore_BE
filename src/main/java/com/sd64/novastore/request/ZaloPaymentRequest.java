package com.sd64.novastore.request;

import com.google.gson.JsonObject;
import com.sd64.novastore.config.ZaloPayConfig;
import com.sd64.novastore.util.payment.HMACUtil;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ZaloPaymentRequest {

    private String app_id;
    private String app_trans_id;
    private String app_time;
    private String app_user;
    private Long amount;
    private String description;
    private String bank_code;
    private String item;
    private String embed_data;
    private String mac;

    public String signatureGen(String secretKey) {
        //app_id +”|”+ app_trans_id +”|”+ app_user +”|”+ amount +"|"+ app_time +”|”+ embed_data +"|"+ item
        String rawHash = this.app_id + "|" +
                this.app_trans_id + "|" +
                this.app_user + "|" +
                this.amount + "|" +
                this.app_time + "|" +
                this.embed_data + "|" +
                this.item;

        return HMACUtil.HMACSHA256Encode(ZaloPayConfig.key1, rawHash);
    }

}
