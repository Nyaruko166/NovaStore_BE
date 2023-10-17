package com.sd64.novastore.request;

import com.sd64.novastore.model.Material;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class MaterialRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    public Material map(Material material){
        material.setName(this.getName());
        material.setCreateDate(new Date());
        material.setUpdateDate(new Date());
        material.setStatus(1);
        return material;
    }
}
