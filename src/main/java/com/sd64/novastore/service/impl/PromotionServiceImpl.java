package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.PromotionDetailDTO;
import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.PromotionDetail;
import com.sd64.novastore.repository.GiamGiaRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.repository.PromotionDetailRepository;
import com.sd64.novastore.repository.PromotionRepository;
import com.sd64.novastore.service.PromotionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PromotionDetailRepository promotionDetailRepository;
    @Autowired
    private GiamGiaRepository giamGiaRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;


    @Override
    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleDeleteExpiredPromotions() {
        List<Promotion> expiredPromotions = getExpiredPromotions();
        for (Promotion promotion : expiredPromotions) {
            processExpiredPromotion(promotion);
        }
    }

    @Override
    public List<Promotion> getExpiredPromotions() {
        Date currentDate = new Date();
        return promotionRepository.findByEndDateBeforeAndStatus(currentDate, 1);
    }

    @Override
    public void processExpiredPromotion(Promotion promotion) {
        List<PromotionDetail> promotionDetails = promotionDetailRepository.findByPromotionId(promotion.getId());
        for (PromotionDetail promotionDetail : promotionDetails) {
            promotionDetail.setStatus(0);
            promotionDetailRepository.save(promotionDetail);
            Product product = promotionDetail.getProduct();
            product.setStatus(1);
            List<ProductDetail> productDetails = giamGiaRepository.findByProductId(product.getId());
            for (ProductDetail productDetail : productDetails) {
                productDetail.setPriceDiscount(productDetail.getPrice());
                productDetailRepository.save(productDetail);
            }
        }
        promotion.setStatus(0);

        try {
            promotionRepository.save(promotion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Promotion> getAll() {
        return promotionRepository.findAllByStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Promotion> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByStatusOrderByIdDesc(pageable);
    }

    public String generateCode() {
        Promotion latestPromotion = promotionRepository.findTopByOrderByIdDesc();

        if (latestPromotion == null || latestPromotion.getId() == null) {
            return "M00001";
        }

        Integer nextId = latestPromotion.getId() + 1;
        String code = String.format("M%05d", nextId);
        return code;
    }


    @Override
    public Boolean add(Promotion promotion) {
        if (checkName(promotion.getName())){
            promotion.setCreateDate(new Date());
            promotion.setUpdateDate(new Date());
            promotion.setCode(generateCode());
            promotion.updateStatus();
             promotionRepository.save(promotion);
             return true;
        }
        return false;
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void updatePromotionStatus() {
        List<Promotion> promotionsToUpdate = promotionRepository.findAllByStatusOrderByIdDesc(2);
        Date currentDate = new Date();
        for (Promotion promotion : promotionsToUpdate) {
            if (promotion.getStartDate().before(currentDate)) {
                promotion.setStatus(1);
                promotionRepository.save(promotion);
            }
        }
    }

    @Override
    public Boolean update(Promotion promotion, Integer id) {
       if (checkName(promotion.getName())){
           Optional<Promotion> optional = promotionRepository.findById(id);
           if (optional.isPresent()) {
               Promotion oldPromotion = optional.get();
               promotion.setId(oldPromotion.getId());
               promotion.setCode(oldPromotion.getCode());
               promotion.setCreateDate(oldPromotion.getCreateDate());
               promotion.setUpdateDate(new Date());
               promotion.setStatus(oldPromotion.getStatus());
               promotion.updateStatus();
               if (oldPromotion.getStatus() != promotion.getStatus() && promotion.getStatus() == 2) {
                   for (PromotionDetail promotionDetail : oldPromotion.getPromotionDetails()) {
                       Product product = promotionDetail.getProduct();
                       if (product.getStatus() == 2) {
                           product.setStatus(1);
                           productRepository.save(product);
                           List<ProductDetail> productDetails = giamGiaRepository.findByProductId(product.getId());
                           for (ProductDetail productDetail : productDetails) {
                               productDetail.setPriceDiscount(productDetail.getPrice());
                               productDetailRepository.save(productDetail);
                               List<PromotionDetail> promotionDetails = promotionDetailRepository.findByPromotionId(id);
                               promotionDetailRepository.deleteAll(promotionDetails);
                           }
                       }
                   }
               }
                promotionRepository.save(promotion);
               return true;
           } else {
               return false;
           }
       }
       return false;
    }


    @Override
    @Transactional
    public Promotion delete(Integer id) {
        Optional<Promotion> optional = promotionRepository.findById(id);
        if (optional.isPresent()) {
            Promotion promotion = optional.get();
            List<PromotionDetail> promotionDetails = promotionDetailRepository.findByPromotionId(id);
            for (PromotionDetail promotionDetail : promotionDetails) {
                promotionDetail.setStatus(0);
                promotionDetailRepository.save(promotionDetail);
                Product product = promotionDetail.getProduct();
                product.setStatus(1);
                List<ProductDetail> productDetails = giamGiaRepository.findByProductId(product.getId());
                for (ProductDetail productDetail : productDetails) {
                    productDetail.setPriceDiscount(productDetail.getPrice());
                    productDetailRepository.save(productDetail);
                }
            }
            promotion.setStatus(0);
            return promotionRepository.save(promotion);
        } else {
            return null;
        }
    }


    @Override
    public Promotion getOne(Integer id) {
        return promotionRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Promotion> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }

    @Override
    public Page<PromotionSearchDTO> findAll(Pageable pageable) {
        return promotionRepository.listPromotions(pageable);
    }

    @Override
    public Page<PromotionSearchDTO> searchPromotion(String code, Date ngayTaoStart,
                                                    Date ngayTaoEnd, Integer status, String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return promotionRepository.searchPromotion(code, ngayTaoStart, ngayTaoEnd, status, name, pageable);
    }

    @Override
    public List<PromotionDetailDTO> getPromotionDetailsByPromotionId(Integer promotionId) {
        return promotionRepository.getPromotionDetailsByPromotionId(promotionId);
    }

    private Boolean checkName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        Promotion promotion = promotionRepository.findByNameAndStatus(name, 1);
        if (promotion != null) {
            return false;
        }
        return true;
    }


}
