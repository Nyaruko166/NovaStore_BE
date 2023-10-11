package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.CategoryRequest;
import com.sd64.novastore.model.Category;
import com.sd64.novastore.repository.CategoryRepository;
import com.sd64.novastore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllByStatus(1);
    }

    @Override
    public Page<Category> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return categoryRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public Category add(CategoryRequest categoryRequest) {
        Category category = categoryRequest.map(new Category());
        return categoryRepository.save(category);
    }

    @Override
    public Category update(CategoryRequest categoryRequest, Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.map(category -> {
            category.setName(categoryRequest.getName());
            category.setCreateDate(Date.valueOf(categoryRequest.getCreateDate()));
            category.setUpdateDate(Date.valueOf(categoryRequest.getUpdateDate()));
            category.setStatus(Integer.valueOf(categoryRequest.getStatus()));
            return categoryRepository.save(category);
        }).orElse(null);
    }

    @Override
    public Category delete(Integer id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.map(category -> {
            category.setStatus(0);
            categoryRepository.save(category);
            return category;
        }).orElse(null);
    }
}
