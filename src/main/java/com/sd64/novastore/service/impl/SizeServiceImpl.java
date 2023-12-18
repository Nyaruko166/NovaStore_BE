package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.SizeRepository;
import com.sd64.novastore.service.SizeService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.attribute.MaterialExcelUtil;
import com.sd64.novastore.utils.attribute.SizeExcelUtil;
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
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeExcelUtil excelUtil;
    @Override
    public List<Size> getAll() {
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Size> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 1);
    }

    private Boolean checkName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        Size size = sizeRepository.findByNameAndStatus(name, 1);
        if (size != null) {
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
        Size sizeFinalPresent = sizeRepository.findTopByOrderByIdDesc();
        if (sizeFinalPresent == null) {
            return "S00001";
        }
        Integer idFinalPresent = sizeFinalPresent.getId() + 1;
        String code = String.format("%04d", idFinalPresent);
        return "S"+code;
    }
    @Override
    public Boolean add(String name) {
        if (checkName(name)) {
            Size size = new Size();
            size.setName(formatName(name));
            size.setStatus(1);
            size.setCreateDate(new Date());
            size.setUpdateDate(new Date());
            size.setCode(generateCode());
            sizeRepository.save(size);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean update(Integer id, String name) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent() && checkName(name)) {
            Size updateSize = optional.get();
            updateSize.setId(id);
            updateSize.setName(formatName(name));
            updateSize.setUpdateDate(new Date());
            sizeRepository.save(updateSize);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Size delete(Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            Size size = optional.get();
            size.setStatus(0);
            size.setUpdateDate(new Date());
            return sizeRepository.save(size);
        } else {
            return null;
        }
    }

    @Override
    public Page<Size> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return sizeRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 1, pageable);
    }

    @Override
    public Size detail(Integer id) {
        Optional<Size> optional = sizeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<Size> getAllSizeDelete(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return sizeRepository.findAllByStatusOrderByUpdateDateDesc(pageable, 0);
    }

    @Override
    public Size restore(Integer id) {
        Optional<Size> optionalSize = sizeRepository.findById(id);
        if (optionalSize.isPresent()) {
            Size restoreSize = optionalSize.get();
            restoreSize.setStatus(1);
            restoreSize.setUpdateDate(new Date());
            return sizeRepository.save(restoreSize);
        } else {
            return null;
        }
    }

    @Override
    public Page<Size> searchDeleted(String name, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return sizeRepository.findAllByNameContainsAndStatusOrderByIdDesc(name.trim(), 0, pageable);
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
