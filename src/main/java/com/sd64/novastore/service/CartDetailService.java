package com.sd64.novastore.service;

import com.sd64.novastore.dto.CartDetailRequest;
import com.sd64.novastore.model.CartDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartDetailService {
    List<CartDetail> getAll();

    Page<CartDetail> getAllPT(Integer page);

    CartDetail add(CartDetailRequest cartDetailRequest);

    CartDetail update(CartDetailRequest cartDetailRequest, Integer id);

    CartDetail delete(Integer id);
}
