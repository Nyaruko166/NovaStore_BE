package com.sd64.novastore.request;

import com.sd64.novastore.model.Color;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ColorRequest {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    public Color map(Color color){
        color.setName(this.getName());
        color.setCreateDate(new Date());
        color.setUpdateDate(new Date());
        color.setStatus(1);
        return color;
    }
}
