package com.sd64.novastore.request;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.ColorDetail;
import com.sd64.novastore.model.ProductDetail;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ColorDetailRequest {
    private Integer colorId;

    private Integer productDetailId;

    public ColorDetail map(ColorDetail colorDetail){
        colorDetail.setColor(Color.builder().id(this.colorId).build());
        colorDetail.setProductDetail(ProductDetail.builder().id(this.productDetailId).build());
        return colorDetail;
    }
}
