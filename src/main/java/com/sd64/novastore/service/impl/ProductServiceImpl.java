package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;


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
        return null;
    }

    @Override
    public Product add(String productName, String description, BigDecimal price, Integer materialId, Integer categoryId,
                       Integer brandId, Integer formId) {
        Product product = new Product();
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
    public Product update(Integer id, String productName, String description, BigDecimal price, Integer materialId,
                          Integer categoryId, Integer brandId, Integer formId) {
        Product product = new Product();
        product.setId(id);
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
}
