package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.Impl.ProductDetailDtoImpl;
import com.sd64.novastore.dto.ProductDetailDto;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.response.ProductDetailSearchResponse;
import com.sd64.novastore.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ProductDetail add(Integer productId, Integer quantity, Integer sizeId, Integer colorId) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setStatus(1);
        productDetail.setCreateDate(new java.util.Date());
        productDetail.setUpdateDate(new java.util.Date());
        productDetail.setProduct(Product.builder().id(productId).build());
        productDetail.setQuantity(quantity);
        productDetail.setSize(Size.builder().id(sizeId).build());
        productDetail.setColor(Color.builder().id(colorId).build());
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail update(Integer id, Integer productId, Integer quantity, Integer sizeId, Integer colorId) {
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(id);
        if (productDetailOptional.isPresent()) {
            ProductDetail productDetail = productDetailOptional.get();
            productDetail.setId(id);
            productDetail.setStatus(productDetail.getStatus());
            productDetail.setCreateDate(productDetail.getCreateDate());
            productDetail.setQuantity(quantity);
            productDetail.setUpdateDate(new Date());
            productDetail.setProduct(Product.builder().id(productId).build());
            productDetail.setColor(Color.builder().id(colorId).build());
            productDetail.setSize(Size.builder().id(sizeId).build());
            return productDetailRepository.save(productDetail);
        }
        return null;
    }

    @Override
    public ProductDetail delete(Integer id) {
        Optional<ProductDetail> optional = productDetailRepository.findById(id);
        if (optional.isPresent()) {
            optional.get().setStatus(0);
            return productDetailRepository.save(optional.get());
        } else {
            return null;
        }
    }

    @Override
    public ProductDetail getOne(Integer id) {
        return productDetailRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProductDetail> getProductDetailByProductId(int page, Integer productId) {
        Pageable pageable = PageRequest.of(page, 5);
        return productDetailRepository.getAllProductDetailByProduct_IdAndStatusOrderByUpdateDateDesc(pageable, productId, 1);
    }

    @Override
    public Page<ProductDetailSearchResponse> getProductBySizeIdAndColorId(int page, Integer productId, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 5);

        var pageProductDetailDto = productDetailRepository.getProductBySizeIdAndColorId(productId, sizeId, colorId, pageable)
                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
        long totalElements = productDetailRepository.getProductBySizeIdAndColorId(productId, sizeId, colorId).stream().count();
        return new PageImpl(pageProductDetailDto, pageable, totalElements);
    }

    public int calculateTotalPages(int totalElemets, int elementsPerpage) {
        int totalPage = totalElemets / elementsPerpage;
        if (totalElemets % elementsPerpage != 0) {
            totalPage++;
        }
        return totalPage;
    }

    @Override
    public int getTotalPage(int page, Integer productId, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 5);
        var pageProductDetailDto = productDetailRepository.getProductBySizeIdAndColorId(productId, sizeId, colorId, pageable)
                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
        int totalElemets = pageProductDetailDto.size();
        int elementsPerpage = 5;
        int totalPage = totalElemets / elementsPerpage;
        if (totalElemets % elementsPerpage != 0) {
            totalPage++;
        }
        return totalPage;
    }
}
