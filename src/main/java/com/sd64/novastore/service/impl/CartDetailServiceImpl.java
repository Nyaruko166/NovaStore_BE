package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.repository.CartDetailRepository;
import com.sd64.novastore.repository.CartRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<CartDetail> getAllCartDetail() {
        return cartDetailRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<CartDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return cartDetailRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public CartDetail add(CartDetail cartDetail) {
        cartDetail.setStatus(1);
        cartDetail.setCreateDate(new java.util.Date());
        cartDetail.setUpdateDate(new java.util.Date());
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDetail update(CartDetail cartDetail, Integer id) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);
        if (cartDetailOptional.isPresent()) {
            CartDetail updateCartDetail = cartDetailOptional.get();
            cartDetail.setId(updateCartDetail.getId());
            cartDetail.setStatus(updateCartDetail.getStatus());
            cartDetail.setCreateDate(updateCartDetail.getCreateDate());
            cartDetail.setUpdateDate(new Date());
            return cartDetailRepository.save(cartDetail);
        }
        return null;
    }

    @Override
    public CartDetail delete(Integer id) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);
        return cartDetailOptional.map(cartDetail -> {
            cartDetail.setStatus(0);
            cartDetailRepository.save(cartDetail);
            return cartDetail;
        }).orElse(null);
    }

    @Override
    public CartDetail getOne(Integer id) {
        return cartDetailRepository.findById(id).orElse(null);
    }


}
