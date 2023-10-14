package com.sd64.novastore.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class VNPaymentResponse implements Serializable {

    private String vnp_ResponseCode;
    private String vnp_CardType;
    private String vnp_Amount;
    private String vnp_TransactionNo;
    private String vnp_PayDate;
    private String vnp_OrderInfo;
    private String vnp_BankTranNo;

}
