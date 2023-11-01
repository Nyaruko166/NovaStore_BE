package com.sd64.novastore.service;

import com.sd64.novastore.model.Image;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ImageService {
    List<Image> getAll();

    Page<Image> getPage(Integer page);

    Image add(Image Image);

    Image update(Image image, Integer id);

    Boolean delete(Integer id);
}
