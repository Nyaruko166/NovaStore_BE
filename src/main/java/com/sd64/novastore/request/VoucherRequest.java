package com.sd64.novastore.request;

import com.sd64.novastore.model.Voucher;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class VoucherRequest {

    private Integer id;

    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotNull(message = "Quantity cannot be left blank")
    private Integer quantity;

    private Boolean type;

    @NotNull(message = "Value cannot be left blank")
    private BigDecimal value;

    @NotNull(message = "MinimumPrice cannot be left blank")
    private BigDecimal minimumPrice;

    @NotNull(message = "MaximumDiscount cannot be left blank")
    private BigDecimal maximumDiscount;

    private Date startDate;

    private Date endDate;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    public Voucher map(Voucher voucher) {
        voucher.setId(this.getId());
        voucher.setName(this.getName());
        voucher.setCode(this.getCode());
        voucher.setQuantity(this.getQuantity());
        voucher.setValue(this.getValue());
        voucher.setStatus(1);
        voucher.setCreateDate(new Date());
        voucher.setUpdateDate(new Date());
        voucher.setEndDate(this.getEndDate());
        voucher.setMinimumPrice(this.getMinimumPrice());
        voucher.setStartDate(this.getStartDate());
        return voucher;
    }
}
