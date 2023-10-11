package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.ImageRequest;
import com.sd64.novastore.model.Image;
import com.sd64.novastore.repository.ImageRepository;
import com.sd64.novastore.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public List<Image> getAll(){
        return  imageRepository.findAllByStatus(1);
    }

    @Override
    public Page<Image> getAll(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return imageRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public Image add(ImageRequest imageRequest) {
        Image image = imageRequest.map(new Image());
        return imageRepository.save(image);
    }

    @Override
    public Image update(ImageRequest imageRequest, Integer id) {
        Optional<Image> optional = imageRepository.findById(id);
        Image image = imageRequest.map(optional.get());
        return imageRepository.save(image);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Image> optional = imageRepository.findById(id);
        if (optional.isPresent()){
            Image image = optional.get();
            imageRepository.delete(image);
            return true;
        } else {
            return false;
        }
    }
}
