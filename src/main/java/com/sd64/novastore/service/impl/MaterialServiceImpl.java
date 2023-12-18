package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Material;
import com.sd64.novastore.repository.MaterialRepository;
import com.sd64.novastore.service.MaterialService;
import com.sd64.novastore.utils.FileUtil;
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
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialExcelUtil excelUtil;

    @Override
    public List<Material> getAll() {
        return materialRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Material> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return materialRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        Material material = materialRepository.findByNameAndStatus(name, 1);
        if (material != null) {
            return false;
        }
        return true;
    }

    public String generateCode() {
        Material materialFinalPresent = materialRepository.findTopByOrderByIdDesc();
        if (materialFinalPresent == null) {
            return "CL00001";
        }
        Integer idFinalPresent = materialFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "CL"+code;
    }

    public String formatName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        return name;
    }

    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Material material = new Material();
            material.setName(formatName(name));
            material.setStatus(1);
            material.setCreateDate(new java.util.Date());
            material.setUpdateDate(new java.util.Date());
            materialRepository.save(material);
            material.setCode(generateCode());
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
            updateMaterial.setName(formatName(material.getName()));
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
        Pageable pageable = PageRequest.of(page, 10);
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
        Pageable pageable = PageRequest.of(page, 10);
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
        Pageable pageable = PageRequest.of(page, 10);
        return materialRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
    }

    @Override
    public Material getOne(Integer id) {
        return materialRepository.findById(id).orElse(null);
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
