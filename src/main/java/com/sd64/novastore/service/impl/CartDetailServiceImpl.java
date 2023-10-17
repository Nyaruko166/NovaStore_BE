package com.sd64.novastore.service.impl;

import com.sd64.novastore.request.CartDetailRequest;
import com.sd64.novastore.model.Cart;
import com.sd64.novastore.model.CartDetail;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.CartDetailRepository;
import com.sd64.novastore.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartDetailServiceImpl implements CartDetailService {
    @Autowired
    private CartDetailRepository cartDetailRepository;

    @Override
    public List<CartDetail> getAll() {
        return cartDetailRepository.findAllByStatus(1);
    }

    @Override
    public Page<CartDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return cartDetailRepository.findAllByStatus(pageable,1);
    }

    @Override
    public CartDetail add(CartDetailRequest cartDetailRequest) {
        CartDetail cartDetail = cartDetailRequest.map(new CartDetail());
        return cartDetailRepository.save(cartDetail);
    }

    @Override
    public CartDetail update(CartDetailRequest cartDetailRequest, Integer id) {
        Optional<CartDetail> cartDetailOptional = cartDetailRepository.findById(id);
        return cartDetailOptional.map(cartDetail -> {
            cartDetail.setQuantity(Integer.valueOf(cartDetailRequest.getQuantity()));
            cartDetail.setPrice(BigDecimal.valueOf(Long.parseLong(cartDetailRequest.getPrice())));
            cartDetail.setCreateDate(Date.from(Instant.parse(cartDetailRequest.getCreateDate())));
            cartDetail.setUpdateDate(Date.from(Instant.parse(cartDetailRequest.getUpdateDate())));
            cartDetail.setStatus(Integer.valueOf(cartDetailRequest.getStatus()));
            cartDetail.setCart(Cart.builder().id(Integer.valueOf(cartDetailRequest.getCartId())).build());
            cartDetail.setProductDetail(ProductDetail.builder().id(Integer.valueOf(cartDetailRequest.getProductDetailId())).build());
            return cartDetailRepository.save(cartDetail);
        }).orElse(null);
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
}
