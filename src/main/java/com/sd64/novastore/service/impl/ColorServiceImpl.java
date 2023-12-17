package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.repository.ColorRepository;
import com.sd64.novastore.service.ColorService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.attribute.ColorExcelUtil;
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
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorExcelUtil excelUtil;
    @Override
    public List<Color> getAll(){
        return colorRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Color> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 10);
        return colorRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        Color color = colorRepository.findByNameAndStatus(name, 1);
        if (color != null) {
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
        Color colorFinalPresent = colorRepository.findTopByOrderByIdDesc();
        if (colorFinalPresent == null) {
            return "M00001";
        }
        Integer idFinalPresent = colorFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "M"+code;
    }

    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Color color = new Color();
            color.setName(formatName(name));
            color.setStatus(1);
            color.setCreateDate(new java.util.Date());
            color.setUpdateDate(new java.util.Date());
            color.setCode(generateCode());
            colorRepository.save(color);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(Integer id, String name) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent() && checkName(name)) {
            Color updateColor = optional.get();
            updateColor.setId(id);
            updateColor.setName(formatName(name));
            updateColor.setUpdateDate(new Date());
            colorRepository.save(updateColor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Color delete(Integer id) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent()) {
            Color color = optional.get();
            color.setStatus(0);
            color.setUpdateDate(new Date());
            return colorRepository.save(color);
        } else {
            return null;
        }
    }

    @Override
    public Page<Color> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return colorRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
    }

    @Override
    public Color detail(Integer id) {
        Optional<Color> optional = colorRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<Color> getAllSizeDelete(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return colorRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Color restore(Integer id) {
        Optional<Color> optionalColor = colorRepository.findById(id);
        if (optionalColor.isPresent()) {
            Color restoreColor = optionalColor.get();
            restoreColor.setStatus(1);
            restoreColor.setUpdateDate(new Date());
            return colorRepository.save(restoreColor);
        } else {
            return null;
        }
    }

    @Override
    public Page<Color> searchDeleted(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return colorRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
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
            } else {
                return "Oke";
            }
        } else {
            return "Lỗi file"; // Lỗi file
        }
    }
}
