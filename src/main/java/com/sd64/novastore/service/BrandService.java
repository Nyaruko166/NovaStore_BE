package com.sd64.novastore.service;

import com.sd64.novastore.dto.BrandRequest;
import com.sd64.novastore.model.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Page<Brand> getAllPT(Integer page);

    Brand add(BrandRequest brandRequest);

    Brand update(BrandRequest brandRequest, Integer id);

    Brand delete(Integer id);
}
