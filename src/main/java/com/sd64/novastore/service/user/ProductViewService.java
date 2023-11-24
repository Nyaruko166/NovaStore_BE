package com.sd64.novastore.service.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.Product;

import java.util.List;

public interface ProductViewService {
    List<Product> getAllProductView();

    Product getOne(Integer id);

    List<Product> getAllProductResponse();
}
