package com.sd64.novastore.request;

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
        form.setName(this.getName());
        form.setCreateDate(new Date());
        form.setUpdateDate(new Date());
        form.setStatus(1);
        return form;
    }
}
