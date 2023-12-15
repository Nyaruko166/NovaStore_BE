package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAll();
    Page<Product> getAll(Integer page);
    Page<ProductDto> getAll(int page);

    Boolean add(String productName, String description, Integer materialId, Integer categoryId, Integer brandId, Integer formId);

    Product update(Integer id, String code, String productName, String description, Integer materialId, Integer categoryId, Integer brandId, Integer formId);

    Product delete(Integer id);

    Product getOne(Integer id);

    Page<ProductDto> search(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, int page);

    Integer importExcelProduct(MultipartFile file) throws IOException;

    Page<ProductDto> getAllProductDeleted(int page);

    Page<ProductDto> searchProductDeleted(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, int page);

    Product restore(Integer id);

    void saveFinal(Product product, List<ProductDetail> listProductDetail, List<MultipartFile> files) throws IOException;

    void updateFinal(Product productBefore, Product productUpdate, List<ProductDetail> listProductDetailUpdate, List<MultipartFile> filesUpdate, List<Integer> imageRemoveIds) throws IOException;

    void update(Product product, Integer productId);
}
