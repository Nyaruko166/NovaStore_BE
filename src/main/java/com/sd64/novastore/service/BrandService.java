package com.sd64.novastore.service;

import com.sd64.novastore.model.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Page<Brand> getPage(Integer page);

    Brand add(Brand brand);

    Brand update(Brand brand, Integer id);

    Brand delete(Integer id);

    Page<Brand> search(String name, int page);

    Brand detail(Integer id);

    Page<Brand> getAllBrandDeleted(int page);
}
