package com.sd64.novastore.response;

import com.sd64.novastore.model.ProductDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateProductDetails {

    private List<ProductDetail> listProductDetail;
}
