package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Image;
import com.sd64.novastore.repository.ImageRepository;
import com.sd64.novastore.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    public Page<Image> getPage(Integer page){
        Pageable pageable = PageRequest.of(page, 5);
        return imageRepository.findAllByStatus(pageable, 1);
    }

    @Override
    public Image add(Image image) {
        image.setStatus(1);
        image.setCreateDate(new Date());
        image.setUpdateDate(new Date());
        return imageRepository.save(image);
    }

    @Override
    public Image update(Image image, Integer id) {
        Optional<Image> optional = imageRepository.findById(id);
        if (optional.isPresent()) {
            Image updateImage = optional.get();
            image.setId(id);
            image.setStatus(updateImage.getStatus());
            image.setCreateDate(updateImage.getCreateDate());
            image.setUpdateDate(new Date());
            return imageRepository.save(image);
        } else {
            return null;
        }
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

    public Image detail(Integer id) {
        Optional<Image> optional = imageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

//    @Override
//    public Page<Image> search(String name, int page) {
//        Pageable pageable = PageRequest.of(page, 5);
//        return imageRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
//    }
}
