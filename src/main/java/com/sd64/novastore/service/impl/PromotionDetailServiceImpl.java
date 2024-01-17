package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.ProductPromotionDTO;
import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.repository.GiamGiaRepository;
import com.sd64.novastore.repository.PrDtRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.repository.PromotionDetailRepository;
import com.sd64.novastore.repository.PromotionRepository;
import com.sd64.novastore.repository.productPromotion;
import com.sd64.novastore.service.PromotionDetailService;
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
public class PromotionDetailServiceImpl implements PromotionDetailService {
    @Autowired
    private PromotionDetailRepository promotionDetailRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PrDtRepository prDtRepository;

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private GiamGiaRepository giamGiaRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private productPromotion productPromotion;

    @Override
    public List<PromotionDetail> getAllPromotionDetail() {
        return promotionDetailRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }


    @Override
    public Page<PromotionDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionDetailRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public PromotionDetail add(PromotionDetail promotionDetail) {
        promotionDetail.setStatus(1);
        promotionDetail.setCreateDate(new java.util.Date());
        promotionDetail.setUpdateDate(new java.util.Date());

        PromotionDetail savedPromotionDetail = promotionDetailRepository.save(promotionDetail);

        Product product = savedPromotionDetail.getProduct();

        Promotion promotion = savedPromotionDetail.getPromotion();

        List<ProductDetail> productDetails = giamGiaRepository.findByProductId(product.getId());
        BigDecimal promotionValue = BigDecimal.valueOf(promotion.getValue());

        for (ProductDetail productDetail : productDetails) {
            BigDecimal price = productDetail.getPrice();
            BigDecimal discount = price.multiply(promotionValue.divide(BigDecimal.valueOf(100)));
            BigDecimal discountedPrice = price.subtract(discount);
            productDetail.setPriceDiscount(discountedPrice);
            productDetailRepository.save(productDetail);
        }
        return savedPromotionDetail;
    }

    @Override
    public PromotionDetail update(PromotionDetail promotionDetail, Integer id) {
        Optional<PromotionDetail> promotionDetailOptional = promotionDetailRepository.findById(id);
        if (promotionDetailOptional.isPresent()) {
            PromotionDetail updatePromotionDetail = promotionDetailOptional.get();
            promotionDetail.setId(updatePromotionDetail.getId());
            promotionDetail.setStatus(updatePromotionDetail.getStatus());
            promotionDetail.setCreateDate(updatePromotionDetail.getCreateDate());
            promotionDetail.setUpdateDate(new Date());
            return promotionDetailRepository.save(promotionDetail);
        }
        return null;
    }

    @Override
    public PromotionDetail delete(Integer id) {
        Optional<PromotionDetail> promotionDetailOptional = promotionDetailRepository.findById(id);
        return promotionDetailOptional.map(promotionDetail -> {
            Product product = promotionDetail.getProduct();
            product.setStatus(1);
            productRepository.save(product);

            List<ProductDetail> productDetails = giamGiaRepository.findByProductId(product.getId());
            for (ProductDetail productDetail : productDetails) {
                productDetail.setPriceDiscount(productDetail.getPrice());
                productDetailRepository.save(productDetail);
            }
            promotionDetail.setStatus(0);
            promotionDetailRepository.save(promotionDetail);
            return promotionDetail;
        }).orElse(null);
    }


    @Override
    public PromotionDetail getOne(Integer id) {
        return promotionDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public List<ProductDetail> getAllPrDT() {
        return prDtRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public List<Product> getProductsByIds(List<Integer> productIds) {
        return productRepository.findAllById(productIds);
    }

    @Override
    public Boolean existsByProductIdAndStatus(Integer productId, Integer status) {
        return promotionDetailRepository.existsByProductIdAndStatus(productId, 1);
    }

    @Override
    public Page<PromotionDetailDTO> All(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionDetailRepository.All(pageable);
    }

    @Override
    public List<ProductDetail> findByProductId(Integer productId) {
        return giamGiaRepository.findByProductId(productId);
    }

    @Override
    public List<ProductPromotionDTO> getAllProductPromotionDTO() {
        return productPromotion.getAllProductPromotionDTO();
    }

    @Override
    public PromotionDetail findByProductIdAndPromotionId(Integer productId, Integer promotionId) {
        return promotionDetailRepository.findByProductIdAndPromotionId(productId, promotionId);
    }


}
