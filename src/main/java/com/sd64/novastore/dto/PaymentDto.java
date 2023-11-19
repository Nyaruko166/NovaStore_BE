package com.sd64.novastore.dto;

import com.sd64.novastore.model.Address;
import com.sd64.novastore.model.Cart;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentDto {

    private Cart cart;
    private Address address;
    private String payUrl;

}
