package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.BrandRequest;
import com.sd64.novastore.model.Brand;
import com.sd64.novastore.repository.BrandRepository;
import com.sd64.novastore.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAllByStatus(1);
    }

    @Override
    public Page<Brand> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByStatus(pageable,1);
    }

    @Override
    public Brand add(BrandRequest brandRequest) {
        Brand brand = brandRequest.map(new Brand());
        return brandRepository.save(brand);
    }

    @Override
    public Brand update(BrandRequest brandRequest, Integer id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        return brandOptional.map(brand -> {
            brand.setName(brandRequest.getName());
            brand.setCreateDate(Date.valueOf(brandRequest.getCreateDate()));
            brand.setUpdateDate(Date.valueOf(brandRequest.getUpdateDate()));
            brand.setStatus(Integer.valueOf(brandRequest.getStatus()));
            return brandRepository.save(brand);
        }).orElse(null);
    }

    @Override
    public Brand delete(Integer id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        return brandOptional.map(brand -> {
            brand.setStatus(0);
            brandRepository.save(brand);
            return brand;
        }).orElse(null);
    }
}
