package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Form;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.repository.MaterialRepository;
import com.sd64.novastore.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAll() {
        return materialRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Material> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        Material material = materialRepository.findByName(name);
        if (material != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean add(Material material) {
        if (checkName(material.getName())) {
            material.setStatus(1);
            material.setCreateDate(new java.util.Date());
            material.setUpdateDate(new java.util.Date());
            materialRepository.save(material);
            material.setCode("C"+material.getId());
            materialRepository.save(material);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(Material material, Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        if (optional.isPresent() && checkName(material.getName())) {
            Material updateMaterial = optional.get();
            updateMaterial.setId(id);
            updateMaterial.setName(material.getName());
            updateMaterial.setUpdateDate(new Date());
            materialRepository.save(updateMaterial);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Material delete(Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        if (optional.isPresent()) {
            Material material = optional.get();
            material.setStatus(0);
            material.setUpdateDate(new Date());
            return materialRepository.save(material);
        } else {
            return null;
        }
    }

    @Override
    public Page<Material> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
    }

    @Override
    public Material detail(Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<Material> getAllDeleted(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Material restore(Integer id) {
        Optional<Material> optionalMaterial = materialRepository.findById(id);
        if (optionalMaterial.isPresent()) {
            Material restoreMaterial = optionalMaterial.get();
            restoreMaterial.setStatus(1);
            restoreMaterial.setUpdateDate(new Date());
            return materialRepository.save(restoreMaterial);
        } else {
            return null;
        }
    }

    @Override
    public Page<Material> searchDelete(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
    }
}
