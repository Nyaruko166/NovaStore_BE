package com.sd64.novastore.service;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> getAllProductDetail();

    Page<ProductDetail> getAllPT(Integer page);

    ProductDetail add(Integer productId, Integer quantity, Integer sizeId, Integer colorId);

    ProductDetail update(ProductDetail productDetail, Integer id);

    ProductDetail delete(Integer id);

    ProductDetail getOne(Integer id);

    Page<ProductDetail> getProductDetailByProductId(int page, Integer productId);


}
