package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Page<Product> getAll(Integer page);
    Page<ProductDto> getAll(int page);

    Boolean add(String code, String productName, String description, Integer materialId, Integer categoryId, Integer brandId, Integer formId);

    Product update(Integer id, String code, String productName, String description, Integer materialId, Integer categoryId, Integer brandId, Integer formId);

    Product delete(Integer id);

    Product getOne(Integer id);

    Page<ProductDto> search(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, int page);

    String importExcelProduct(MultipartFile file) throws IOException;

    Page<ProductDto> getAllProductDeleted(int page);

    Page<ProductDto> searchProductDeleted(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, int page);

    void restore(List<Integer> listInteger);

}
