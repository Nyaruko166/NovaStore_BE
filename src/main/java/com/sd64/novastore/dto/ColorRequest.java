package com.sd64.novastore.dto;

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

    public Color dto(Color color){
        color.setName(this.name);
        color.setCreateDate(this.createDate);
        color.setUpdateDate(this.updateDate);
        color.setStatus(this.status);
        return color;
    }
}
