package com.sd64.novastore.service;

import com.sd64.novastore.dto.Impl.ProductDetailDtoImpl;
import com.sd64.novastore.dto.ProductDetailDto;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.response.ProductDetailSearchResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> getAllProductDetail();

    Page<ProductDetail> getAllPT(Integer page);

    ProductDetail add(Integer productId, Integer quantity, Integer sizeId, Integer colorId);

    ProductDetail update(Integer id, Integer productId, Integer quantity, Integer sizeId, Integer colorId);

    ProductDetail delete(Integer id);

    ProductDetail getOne(Integer id);

    Page<ProductDetail> getProductDetailByProductId(int page, Integer productId);

    Page<ProductDetailSearchResponse> getProductBySizeIdAndColorId(int page, Integer productId, Integer sizeId, Integer colorId);

    int getTotalPage(int page, Integer productId, Integer sizeId, Integer colorId);

}
