package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.repository.BrandRepository;
import com.sd64.novastore.service.BrandService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.attribute.BrandExcelUtil;
import com.sd64.novastore.utils.attribute.MaterialExcelUtil;
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
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private BrandExcelUtil excelUtil;

    @Override
    public List<Brand> getAll() {
        return brandRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Brand> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return brandRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");

        Brand brand = brandRepository.findByNameAndStatus(name, 1);
        if (brand != null) {
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
        Brand brandFinalPresent = brandRepository.findTopByOrderByIdDesc();
        if (brandFinalPresent == null) {
            return "TH00001";
        }
        Integer idFinalPresent = brandFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "TH" + code;
    }

    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Brand brand = new Brand();
            brand.setName(formatName(name));
            brand.setStatus(1);
            brand.setCreateDate(new Date());
            brand.setUpdateDate(new Date());
            brand.setCode(generateCode());
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
            updateBrand.setName(formatName(name));
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
        Pageable pageable = PageRequest.of(page, 10);
        return brandRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
    }

    @Override
    public Page<Brand> searchDelete(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
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
        Pageable pageable = PageRequest.of(page, 10);
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
            } else if (status.contains("Tồn tại")) {
                return "Tồn tại";
            } else {
                return "Oke";
            }
        } else {
            return "Lỗi file"; // Lỗi file
        }
    }
}
