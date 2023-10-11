package com.sd64.novastore.dto;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter

public class ProductDetailRequest {
    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotNull(message = "Số lượng không được để trống")
    private Integer quantity;

    @NotNull(message = "Giá không được để trống")
    private BigDecimal price;

    private Date createDate;

    private Date updateDate;

    private Integer status;

    private Integer productId;

    public ProductDetail map(ProductDetail productDetail){
        productDetail.setDescription(this.description);
        productDetail.setQuantity(this.quantity);
        productDetail.setPrice(this.price);
        productDetail.setCreateDate(this.createDate);
        productDetail.setUpdateDate(this.updateDate);
        productDetail.setStatus(this.status);
        productDetail.setProduct(Product.builder().id(this.productId).build());
        return productDetail;
    }
}
