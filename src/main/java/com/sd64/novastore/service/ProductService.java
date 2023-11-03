package com.sd64.novastore.service;

import com.sd64.novastore.dto.ProductDto;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.request.ProductRequest;
import com.sd64.novastore.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Page<Product> getAll(Integer page);

    Product add(ProductRequest productRequest);

    Product update(ProductRequest productRequest, Integer id);

    Boolean delete(Integer id);
}
