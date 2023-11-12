package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.user.UserProductDetailRepository;
import com.sd64.novastore.service.user.UserProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserProductDetailServiceImpl implements UserProductDetailService {
    @Autowired
    private UserProductDetailRepository userProductDetailRepository;

    @Override
    public List<ProductDetail> getAllProductDetail(Integer id) {
        return userProductDetailRepository.getAllProductDetail(id);
    }

    @Override
    public Integer getProductDetailId(Integer productId, Integer sizeId, Integer colorId) {
        return userProductDetailRepository.getProductDetailId(productId, sizeId, colorId);
    }

    @Override
    public ProductDetail getProductDetailById(Integer id) {
        return userProductDetailRepository.findById(id).orElse(null);
    }
}
