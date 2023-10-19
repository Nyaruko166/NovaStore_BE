package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.request.BrandRequest;
import com.sd64.novastore.model.Brand;
import com.sd64.novastore.repository.BrandRepository;
import com.sd64.novastore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Brand> getPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByStatusOrderByUpdateDateDesc(pageable,1);
    }

    @Override
    public Brand add(Brand brand) {
        brand.setStatus(1);
        brand.setCreateDate(new Date());
        brand.setUpdateDate(new Date());
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(Brand brand, Integer id) {
        Optional<Brand> optional = brandRepository.findById(id);
        if (optional.isPresent()) {
            Brand updateBrand = optional.get();
            brand.setId(id);
            brand.setName(brand.getName());
            brand.setStatus(updateBrand.getStatus());
            brand.setCreateDate(updateBrand.getCreateDate());
            brand.setUpdateDate(new Date());
            return brandRepository.save(brand);
        } else {
            return null;
        }
    }

    @Override
    public Brand delete(Integer id) {
        Optional<Brand> optional = brandRepository.findById(id);
        if (optional.isPresent()) {
            Brand brand = optional.get();
            brand.setStatus(0);
            return brandRepository.save(brand);
        } else {
            return null;
        }
    }

    @Override
    public Page<Brand> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }

    @Override
    public Brand detail(Integer id) {
        Optional<Brand> optional = brandRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}
