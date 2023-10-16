package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.ProductDto;
import com.sd64.novastore.request.ProductRequest;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<ProductDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.getAllProduct(pageable);
    }

    @Override
    public Page<Product> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public Product add(ProductRequest productRequest) {
        Product product = productRequest.map(new Product());
        return productRepository.save(product);
    }

    @Override
    public Product update(ProductRequest productRequest, Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        Product product = productRequest.map(optional.get());
        return productRepository.save(product);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setStatus(0);
            productRepository.save(product);
            return true;
        } else {
            return false;
        }
    }
}
