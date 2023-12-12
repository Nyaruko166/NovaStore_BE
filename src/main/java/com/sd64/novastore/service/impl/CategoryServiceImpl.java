package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Brand;
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
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private static final String PREFIX = "L";
    private static final int INITIAL_COUNTER = 1;
    private static final int PADDING_LENGTH = 4;
    private static AtomicInteger counter = new AtomicInteger(INITIAL_COUNTER);
    public static String generateProductCode() {
        int currentCounter = counter.getAndIncrement();
        String paddedCounter = String.format("%0" + PADDING_LENGTH + "d", currentCounter);
        return PREFIX + paddedCounter;
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Category> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return categoryRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category != null) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Category category = new Category();
            category.setName(name);
            category.setStatus(1);
            category.setCreateDate(new java.util.Date());
            category.setUpdateDate(new java.util.Date());
            category.setCode(generateProductCode());
            categoryRepository.save(category);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(Category category, Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent() && checkName(category.getName())) {
            Category updateCategory = optional.get();
            updateCategory.setId(id);
            updateCategory.setName(category.getName());
            updateCategory.setUpdateDate(new Date());
            categoryRepository.save(updateCategory);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Category delete(Integer id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setStatus(0);
            category.setUpdateDate(new Date());
            return categoryRepository.save(category);
        } else {
            return null;
        }
    }

    @Override
    public Page<Category> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return categoryRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
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

    @Override
    public Page<Category> getAllDeleted(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return categoryRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Category restore(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            Category restoreCategory = optionalCategory.get();
            restoreCategory.setStatus(1);
            restoreCategory.setUpdateDate(new Date());
            return categoryRepository.save(restoreCategory);
        } else {
            return null;
        }
    }

    @Override
    public Page<Category> searchDelete(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return categoryRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
    }
}
