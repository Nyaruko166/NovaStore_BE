package com.sd64.novastore.service;

import com.sd64.novastore.model.CartDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CartDetailService {
    List<CartDetail> getAllCartDetail();

    Page<CartDetail> getAllPT(Integer page);

    CartDetail add(CartDetail cartDetail);

    CartDetail update(CartDetail cartDetail, Integer id);

    CartDetail delete(Integer id);

    CartDetail getOne(Integer id);

}
