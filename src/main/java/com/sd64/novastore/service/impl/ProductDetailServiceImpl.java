package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;


    @Override
    public List<ProductDetail> getAllProductDetail() {
        return productDetailRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<ProductDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productDetailRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public ProductDetail add(ProductDetail productDetail) {
        productDetail.setStatus(1);
        productDetail.setCreateDate(new java.util.Date());
        productDetail.setUpdateDate(new java.util.Date());
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail update(ProductDetail productDetail, Integer id) {
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(id);
        if (productDetailOptional.isPresent()) {
            ProductDetail updateProductDetail = productDetailOptional.get();
            productDetail.setId(updateProductDetail.getId());
            productDetail.setStatus(updateProductDetail.getStatus());
            productDetail.setCreateDate(updateProductDetail.getCreateDate());
            productDetail.setUpdateDate(new Date());
            return productDetailRepository.save(productDetail);
        }
        return null;
    }

    @Override
    public ProductDetail delete(Integer id) {
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(id);
        return productDetailOptional.map(productDetail -> {
            productDetail.setStatus(0);
            productDetailRepository.save(productDetail);
            return productDetail;
        }).orElse(null);
    }

    @Override
    public ProductDetail getOne(Integer id) {
        return productDetailRepository.findById(id).orElse(null);
    }


}
