package com.sd64.novastore.service;

import com.sd64.novastore.request.ProductDetailRequest;
import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> getAll();

    Page<ProductDetail> getAll(Integer page);

    ProductDetail add(ProductDetailRequest productDetailRequest);

    ProductDetail update(ProductDetailRequest productDetailRequest, Integer id);

    Boolean delete(Integer id);
}
