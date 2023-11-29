package com.sd64.novastore.service.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.dto.common.ProductResponseHomeDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;

import java.util.List;

public interface ProductViewService {
    List<Product> getAllProductView();

    Product getOne(Integer id);

//    List<Product> getAllProductResponse();

    ProductResponseHomeDto getProductResponse();

    List<ProductResponseHomeDto> getAllProductResponse();
}
