package com.sd64.novastore.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PaymentResponse implements Serializable {

    private String status;
    private String message;
    private String URL;

}
