package com.sd64.novastore.dto;

import com.sd64.novastore.model.Image;
import com.sd64.novastore.model.ProductDetail;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class ImageRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    private Integer productDetailId;

    public Image dto(Image image){
        image.setName(this.name);
        image.setCreateDate(this.createDate);
        image.setUpdateDate(this.updateDate);
        image.setStatus(this.status);
        image.setProductDetail(ProductDetail.builder().id(this.productDetailId).build());
        return image;
    }
}
