package com.sd64.novastore.service;

import com.sd64.novastore.model.Brand;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BrandService {
    List<Brand> getAll();

    Page<Brand> getPage(Integer page);

    Boolean add(Brand brand);

    Boolean update(Integer id, String name);

    Brand delete(Integer id);

    Page<Brand> search(String name, int page);

    Brand detail(Integer id);

    Page<Brand> getAllDeleted(int page);

    Brand restore(Integer id);

    Page<Brand> searchDelete(String name, int page);
}
