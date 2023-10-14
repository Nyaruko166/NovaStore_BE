package com.sd64.novastore.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ZaloPaymentResponse implements Serializable {

    private String amount;
    private String apptransid;
    private String bankcode;
    private String discountamount;
    private String pmcid;
    private String status;

}
