package com.sd64.novastore.service;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.response.ProductDetailSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductDetailService {
    List<ProductDetail> getProductDetailByProductId(Integer productId);

    Page<ProductDetail> getAllPT(Integer page);

    Boolean add(Integer productId, Integer quantity, BigDecimal price, Integer sizeId, Integer colorId);

    ProductDetail update(Integer id, Integer productId, Integer quantity, BigDecimal price, Integer sizeId, Integer colorId);

    ProductDetail delete(Integer id);

    ProductDetail getOne(Integer id);


    Page<ProductDetailSearchResponse> getProductByPriceAndSizeIdAndColorId(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId);

    String importExcelProduct(MultipartFile file, Integer productId) throws IOException;

    int getTotalPage(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId);

    Page<ProductDetailSearchResponse> getProductByPriceAndSizeIdAndColorIdDeleted(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId);

    ProductDetail restore(Integer id);

    int getTotalPageDeleted(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId);

    byte[] getProductDetail(Integer id);
}
