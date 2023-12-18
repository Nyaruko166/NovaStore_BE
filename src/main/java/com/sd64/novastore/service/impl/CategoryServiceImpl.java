package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.repository.CategoryRepository;
import com.sd64.novastore.service.CategoryService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.attribute.CategoryExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryExcelUtil excelUtil;

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
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        Category category = categoryRepository.findByNameAndStatus(name, 1);
        if (category != null) {
            return false;
        }
        return true;
    }

    public String formatName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        return name;
    }

    public String generateCode() {
        Category categoryFinalPresent = categoryRepository.findTopByOrderByIdDesc();
        if (categoryFinalPresent == null) {
            return "L00001";
        }
        Integer idFinalPresent = categoryFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "L"+code;
    }

    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Category category = new Category();
            category.setName(formatName(name));
            category.setStatus(1);
            category.setCreateDate(new java.util.Date());
            category.setUpdateDate(new java.util.Date());
            category.setCode(generateCode());
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
            updateCategory.setName(formatName(category.getName()));
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

    @Override
    public String importExcel(MultipartFile file) throws IOException {
        if (excelUtil.isValidExcel(file)) {
            String uploadDir = "./src/main/resources/static/filecustom/size/";
            String fileName = file.getOriginalFilename();
            String excelPath = FileUtil.copyFile(file, fileName, uploadDir);
            String status = excelUtil.getFromExcel(excelPath);
            if (status.contains("Sai dữ liệu")) {
                return "Sai dữ liệu";
            } else if (status.contains("Trùng")) {
                return "Trùng";
            } else if (status.contains("Tồn tại")){
                return "Tồn tại";
            } else if (status.contains("Trống")) {
                return "Trống";
            } else {
                return "Oke";
            }
        } else {
            return "Lỗi file"; // Lỗi file
        }
    }
}
