package com.sd64.novastore.dto;

import com.sd64.novastore.model.Form;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class FormRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    public Form map(Form form){
        form.setName(this.name);
        form.setCreateDate(this.createDate);
        form.setUpdateDate(this.updateDate);
        form.setStatus(this.status);
        return form;
    }
}
