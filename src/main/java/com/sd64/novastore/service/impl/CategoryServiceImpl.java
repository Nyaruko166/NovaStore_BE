package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.request.CategoryRequest;
import com.sd64.novastore.model.Category;
import com.sd64.novastore.repository.CategoryRepository;
import com.sd64.novastore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Category> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return categoryRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    @Override
    public Category add(Category category) {
        category.setStatus(1);
        category.setCreateDate(new java.util.Date());
        category.setUpdateDate(new java.util.Date());
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category, Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category updateCategory = optional.get();
            category.setId(id);
            category.setStatus(updateCategory.getStatus());
            category.setCreateDate(updateCategory.getCreateDate());
            category.setUpdateDate(new Date());
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public Category delete(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setStatus(0);
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public Page<Category> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return categoryRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }

    @Override
    public Category detail(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }
}
