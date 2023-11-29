package com.sd64.novastore.service.impl;

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
        return brandRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Brand> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByStatusOrderByUpdateDateDesc(pageable,1);
    }

    private Boolean checkName(String name) {
        Brand brand = brandRepository.findByName(name);
        if (brand != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean add(Brand brand) {
        if (checkName(brand.getName())) {
            brand.setStatus(1);
            brand.setCreateDate(new Date());
            brand.setUpdateDate(new Date());
            brandRepository.save(brand);
            brand.setCode("T"+brand.getId());
            brandRepository.save(brand);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(Integer id, String name) {
        Optional<Brand> optional = brandRepository.findById(id);
        if (optional.isPresent() && checkName(name)) {
            Brand updateBrand = optional.get();
            updateBrand.setName(name);
            updateBrand.setUpdateDate(new Date());
            brandRepository.save(updateBrand);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Brand delete(Integer id) {
        Optional<Brand> optional = brandRepository.findById(id);
        if (optional.isPresent()) {
            Brand brand = optional.get();
            brand.setStatus(0);
            brand.setUpdateDate(new Date());
            return brandRepository.save(brand);
        } else {
            return null;
        }
    }

    @Override
    public Page<Brand> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
    }

    @Override
    public Page<Brand> searchDelete(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
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

    @Override
    public Page<Brand> getAllDeleted(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Brand restore(Integer id) {
        Optional<Brand> optionalBrand = brandRepository.findById(id);
        if (optionalBrand.isPresent()) {
            Brand restoreBrand = optionalBrand.get();
            restoreBrand.setStatus(1);
            restoreBrand.setUpdateDate(new Date());
            return brandRepository.save(restoreBrand);
        } else {
            return null;
        }
    }
}
