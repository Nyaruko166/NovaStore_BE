package com.sd64.novastore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SessionCart {
    private BigDecimal totalPrice;

    private Integer totalItems;

    private Set<SessionCartItem> cartDetails;
}
