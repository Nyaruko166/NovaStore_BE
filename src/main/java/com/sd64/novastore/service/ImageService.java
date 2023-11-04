package com.sd64.novastore.service;

import com.sd64.novastore.model.Image;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<Image> getAll();

    Page<Image> getImageByProductDetail(int page, Integer productDetailId);

    void add(Integer productDetailId, List<MultipartFile> images);

    Image update(Image image, Integer id);

    Boolean delete(Integer id);

    byte[] get(Integer imageId);
}
