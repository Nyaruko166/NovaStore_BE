package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.service.ProductService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.product.ProductExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductExcelUtil productExcelUtil;


    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Page<Product> getAll(Integer page) {
        return null;
    }

    @Override
    public Page<ProductDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.getAllProduct(pageable);
    }

    public Boolean checkCode(String code) {
        Optional<Product> optionalProduct = productRepository.findAllByCode(code);
        if (optionalProduct.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean add(String code, String productName, String description, BigDecimal price, Integer materialId, Integer categoryId,
                       Integer brandId, Integer formId) {
        if (checkCode(code)) {
            Product product = new Product();
            product.setCode(code);
            product.setName(productName);
            product.setStatus(1);
            product.setDescription(description);
            product.setCreateDate(new Date());
            product.setUpdateDate(new Date());
            product.setPrice(price);
            product.setMaterial(Material.builder().id(materialId).build());
            product.setCategory(Category.builder().id(categoryId).build());
            product.setBrand(Brand.builder().id(brandId).build());
            product.setForm(Form.builder().id(formId).build());
            productRepository.save(product);
            return true;
        }
        return false;
    }

    @Override
    public Product update(Integer id, String code, String productName, String description, BigDecimal price, Integer materialId,
                          Integer categoryId, Integer brandId, Integer formId) {
        Product product = new Product();
        product.setId(id);
        product.setCode(code);
        product.setName(productName);
        product.setStatus(1);
        product.setDescription(description);
        product.setCreateDate(new Date());
        product.setUpdateDate(new Date());
        product.setPrice(price);
        product.setMaterial(Material.builder().id(materialId).build());
        product.setCategory(Category.builder().id(categoryId).build());
        product.setBrand(Brand.builder().id(brandId).build());
        product.setForm(Form.builder().id(formId).build());
        return productRepository.save(product);
    }

    @Override
    public Product delete(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setStatus(0);
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    @Override
    public Page<ProductDto> search(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, BigDecimal priceMin, BigDecimal priceMax, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.search(pageable, brandId, categoryId, formId, materialId, productName, description, priceMin, priceMax);
    }

    @Override
    public Product getOne(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public String importExcelProduct(MultipartFile file) throws IOException {
        if (productExcelUtil.isValidExcel(file)) {
            String uploadDir = "./src/main/resources/static/filecustom/product/";
            String fileName = file.getOriginalFilename();
            String excelPath = FileUtil.copyFile(file, fileName, uploadDir);

            String status = productExcelUtil.getProductFromExcel(excelPath);
            if (status.contains("Trùng mã")) {
                return "Trùng mã";
            } else if (status.contains("Sai dữ liệu")) {
                return "Sai dữ liệu";
            } else {
                return "Oke bạn ơi";
            }
        } else {
            return "lỗi file";
        }
    }

    @Override
    public Page<ProductDto> getAllProductDeleted(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.getAllProductDeleted(pageable);
    }

    @Override
    public Page<ProductDto> searchProductDeleted(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, BigDecimal priceMin, BigDecimal priceMax, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.searchProductDeleted(pageable, brandId, categoryId, formId, materialId, productName, description, priceMin, priceMax);
    }
}
