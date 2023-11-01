package com.sd64.novastore.service;

import com.sd64.novastore.dto.ProductDto;
import com.sd64.novastore.model.Product;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<ProductDto> getAll(int page);

//    Page<Product> getAll(Integer page);

    Product add(String productName, String description, BigDecimal price, Integer materialId, Integer categoryId, Integer brandId, Integer formId);

    Product update(Integer id, String productName, String description, BigDecimal price, Integer materialId, Integer categoryId, Integer brandId, Integer formId);

    Product delete(Integer id);

    Product getOne(Integer id);

    Page<ProductDto> search(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, BigDecimal priceMin, BigDecimal priceMax, int page);
}
