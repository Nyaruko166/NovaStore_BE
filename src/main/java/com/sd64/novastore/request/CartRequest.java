package com.sd64.novastore.request;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Cart;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class CartRequest {

    private String createDate;

    private String accountId;

    public Cart map(Cart cart) {
        cart.setCreateDate(Date.from(Instant.parse(this.getCreateDate())));
        cart.setAccount(Account.builder().id(Integer.valueOf(this.getAccountId())).build());
        return cart;
    }
}
