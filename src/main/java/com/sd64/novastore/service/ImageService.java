package com.sd64.novastore.service;

import com.sd64.novastore.dto.ImageRequest;
import com.sd64.novastore.model.Image;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImageService {
    List<Image> getAll();

    Page<Image> getAll(Integer page);

    Image add(ImageRequest imageRequest);

    Image update(ImageRequest imageRequest, Integer id);

    Boolean delete(Integer id);
}
