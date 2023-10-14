package com.sd64.novastore.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MomoPaymentResponse implements Serializable {

    private String resultCode;
    private String message;
    private String transId;
    private String orderId;
    private String amount;
    private String requestId;

}
