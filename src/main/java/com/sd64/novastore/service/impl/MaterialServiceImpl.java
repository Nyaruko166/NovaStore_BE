package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Form;
import com.sd64.novastore.request.MaterialRequest;
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
public class MaterialServiceImpl implements MaterialService{
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public List<Material> getAll(){
        return  materialRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Material> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Material add(Material material) {
        material.setStatus(1);
        material.setCreateDate(new java.util.Date());
        material.setUpdateDate(new java.util.Date());
        return materialRepository.save(material);
    }

    @Override
    public Material update(Material material, Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        if (optional.isPresent()) {
            Material updateMaterial = optional.get();
            material.setId(id);
            material.setName(updateMaterial.getName());
            material.setStatus(updateMaterial.getStatus());
            material.setCreateDate(updateMaterial.getCreateDate());
            material.setUpdateDate(new Date());
            return materialRepository.save(material);
        } else {
            return null;
        }
    }

    @Override
    public Material delete(Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        if (optional.isPresent()) {
            Material material = optional.get();
            material.setStatus(0);
            return materialRepository.save(material);
        } else {
            return null;
        }
    }

    @Override
    public Page<Material> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
