package com.sd64.novastore.request;

import com.sd64.novastore.model.Promotion;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PromotionRequest {

    private Integer id;

    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private Integer type;

    @NotNull(message = "Value cannot be empty")
    private BigDecimal value;

//    @Pattern(regexp = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[0-1])")
    private Date startDate;

    private Date endDate;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    public Promotion map(Promotion promotion) {
        promotion.setId(this.getId());
        promotion.setCode(this.getCode());
        promotion.setName(this.getName());
//        promotion.setType(this.getType());
        promotion.setValue(this.getValue());
//        promotion.setStartDate(this.getStartDate());
        promotion.setEndDate(this.getEndDate());
        promotion.setCreateDate(new Date());
        promotion.setUpdateDate(new Date());
        promotion.setStatus(1);
        return promotion;
    }
}
