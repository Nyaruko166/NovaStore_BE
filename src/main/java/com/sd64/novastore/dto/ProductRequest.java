package com.sd64.novastore.dto;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class ProductRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    private Integer categoryId;

    private Integer brandId;

    private Integer materialId;

    private Integer formId;

    public Product dto(Product product){
        product.setName(this.name);
        product.setCreateDate(this.createDate);
        product.setUpdateDate(this.updateDate);
        product.setStatus(this.status);
        product.setCategory(Category.builder().id(this.categoryId).build());
        product.setBrand(Brand.builder().id(this.brandId).build());
        product.setMaterial(Material.builder().id(this.materialId).build());
        product.setForm(Form.builder().id(this.formId).build());
        return product;
    }
}
