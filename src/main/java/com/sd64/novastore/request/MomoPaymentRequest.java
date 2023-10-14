package com.sd64.novastore.request;

import com.sd64.novastore.config.ZaloPayConfig;
import com.sd64.novastore.util.payment.HMACUtil;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MomoPaymentRequest {

    private String partnerCode;
    private String requestId;
    private Long amount;
    private String orderId;
    private String orderInfo;
    private String redirectUrl;
    private String ipnUrl;
    private String requestType;
    private String extraData;
    private String lang;
    private String signature;

    public String signatureGen(String accessKey, String secretKey) {
        String rawHash = "accessKey=" + accessKey +
                "&amount=" + this.amount +
                "&extraData=" + this.extraData +
                "&ipnUrl=" + this.ipnUrl +
                "&orderId=" + this.orderId +
                "&orderInfo=" + this.orderInfo +
                "&partnerCode=" + this.partnerCode +
                "&redirectUrl=" + this.redirectUrl +
                "&requestId=" + this.requestId +
                "&requestType=" + this.requestType;
        return HMACUtil.HMacHexStringEncode(HMACUtil.HMACSHA256, secretKey, rawHash);
    }

}
