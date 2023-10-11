package com.sd64.novastore.request;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

public class PaymentMethodRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Số tiền không được để trống")
    private BigDecimal money;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    private Integer status;

    private Integer billId;

    public PaymentMethod map(PaymentMethod paymentMethod){
        paymentMethod.setName(this.name);
        paymentMethod.setMoney(this.money);
        paymentMethod.setDescription(this.description);
        paymentMethod.setStatus(this.status);
        paymentMethod.setBill(Bill.builder().id(this.billId).build());
        return paymentMethod;
    }
}
