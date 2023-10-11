package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.ProductDetailRequest;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<ProductDetail> getAll(){
        return  productDetailRepository.findAllByStatus(1);
    }

    @Override
    public Page<ProductDetail> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return productDetailRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public ProductDetail add(ProductDetailRequest productDetailRequest) {
        ProductDetail productDetail = productDetailRequest.map(new ProductDetail());
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail update(ProductDetailRequest productDetailRequest, Integer id) {
        Optional<ProductDetail> optional = productDetailRepository.findById(id);
        ProductDetail productDetail = productDetailRequest.map(optional.get());
        return productDetailRepository.save(productDetail);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<ProductDetail> optional = productDetailRepository.findById(id);
        if (optional.isPresent()){
            ProductDetail productDetail = optional.get();
            productDetail.setStatus(0);
            productDetailRepository.save(productDetail);
            return true;
        } else {
            return false;
        }
    }
}
