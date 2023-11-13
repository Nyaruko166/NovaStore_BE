package com.sd64.novastore.service.user;

import com.sd64.novastore.model.ProductDetail;

import java.util.List;

public interface UserProductDetailService {
    List<ProductDetail> getAllProductDetail(Integer id);

    Integer getProductDetailId(Integer productId, Integer sizeId, Integer colorId);

    ProductDetail getProductDetailById(Integer id);

    ProductDetail getProductDetail(Integer productId, Integer sizeId, Integer colorId);
}
