package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.request.MaterialRequest;
import com.sd64.novastore.model.Material;
import com.sd64.novastore.repository.MaterialRepository;
import com.sd64.novastore.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Page<Material> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Material add(MaterialRequest materialRequest) {
        Material material = materialRequest.map(new Material());
        return materialRepository.save(material);
    }

    @Override
    public Material update(MaterialRequest materialRequest, Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        Material material = materialRequest.map(optional.get());
        return materialRepository.save(material);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Material> optional = materialRepository.findById(id);
        if (optional.isPresent()){
            Material material = optional.get();
            material.setStatus(0);
            materialRepository.save(material);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Page<Material> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
