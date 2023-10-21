package com.sd64.novastore.service;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.request.PromotionRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PromotionService {

    List<Promotion> getAll();

    Page<Promotion> getPage(Integer page);

    Promotion add(Promotion promotion);

    Promotion update(Promotion promotion, Integer id);

    Promotion delete(Integer id);

    Page<Promotion> search(String name, int page);

    Promotion detail(Integer id);
}
