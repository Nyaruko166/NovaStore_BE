package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Image;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.repository.ImageRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.service.ImageService;
import com.sd64.novastore.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<Image> getAll(){
        return  imageRepository.findAllByStatus(1);
    }

    @Override
    public List<Image> getAllImageByProductIdNoStatus(Integer productId) {
        return imageRepository.findAllByProduct_Id(productId);
    }

    @Override
    public Page<Image> getImageByProduct(int page, Integer productId){
        Pageable pageable = PageRequest.of(page, 5);
        return imageRepository.getAllImageByProduct_IdAndStatusOrderByUpdateDateDesc(pageable, productId, 1);
    }

    @Override
    public void add(Integer productId, List<MultipartFile> images) {
        log.info("Start upload image, product id = {}, images = {}", productId, images);
        String uploadDir = "./src/main/resources/static/assets/product/";
        for (var image: images) {
            String fileName = image.getOriginalFilename();
            Image imageAdd = new Image();
            imageAdd.setStatus(1);
            imageAdd.setCreateDate(new Date());
            imageAdd.setUpdateDate(new Date());
            imageAdd.setProduct(Product.builder().id(productId).build());
            Image imageLasted = imageRepository.save(imageAdd);
            String uid = "product_" + imageLasted.getId();
            String avtPath = FileUtil.copyFile(image, fileName, uploadDir);
            String imageUrl = FileUtil.rename(avtPath, uid);
            imageLasted.setName(imageUrl);
            imageRepository.save(imageLasted);
            log.info("Save done image = {}", imageLasted);
        }

    }

    @Override
    public void update(Integer id, Integer productId, MultipartFile image) {
        Optional<Image> optional = imageRepository.findById(id);
        if (optional.isPresent()) {
            String uploadDir = "./src/main/resources/static/assets/product/";
            String fileName = image.getOriginalFilename();
            Image updateImage = optional.get();
            updateImage.setId(id);
            updateImage.setStatus(updateImage.getStatus());
            updateImage.setCreateDate(updateImage.getCreateDate());
            updateImage.setUpdateDate(new Date());
            updateImage.setProduct(Product.builder().id(productId).build());
            String uid = "product_" + updateImage.getId();
            String extension = FileUtil.getFileExtension(fileName);
            String newFileName = uid+ "." + extension;
            FileUtil.copyFile(image, newFileName, uploadDir);
            updateImage.setName(newFileName);
            imageRepository.save(updateImage);
        }
    }

    @Override
    public Integer getProductDetailByIdImage(Integer imageId) {
        Integer productId = imageRepository.findById(imageId).get().getProduct().getId();
        return productId;
    }

    @Override
    public List<Image> getImageResponse(List<Image> listImage) {
        List<Image> listImageResponse = new ArrayList<>();
        for (Image image: listImage) {
            if (image.getStatus() == 1) {
                listImageResponse.add(image);
            }
        }
        Collections.sort(listImageResponse, new Comparator<Image>() {
            @Override
            public int compare(Image image1, Image image2) {
                return image2.getId().compareTo(image1.getId());
            }
        });
        return listImageResponse;
    }

    @Override
    public Image delete(Integer id) {
        Optional<Image> optional = imageRepository.findById(id);
        if (optional.isPresent()){
            Image image = optional.get();
            image.setStatus(0);
            image.setUpdateDate(new Date());
            return imageRepository.save(image);
        } else {
            return null;
        }
    }

    @Override
    public byte[] getImage(Integer imageId) {
        var image = imageRepository.findByIdAndStatus(imageId, 1);
        if (image == null) {
            log.info("image id = {} is not exist on DB", imageId);
            return null;
        }
        try {
            return convert(getImagePath(image.getName()));
        } catch (IOException e) {
            log.error("Convert image fail, image id = {}", imageId);
            return null;
        }
    }

    @Override
    public byte[] getImageDeleted(Integer imageId) {
        var image = imageRepository.findByIdAndStatus(imageId, 0);
        if (image == null) {
            log.info("image id = {} is not exist on DB", imageId);
            return null;
        }
        try {
            return convert(getImagePath(image.getName()));
        } catch (IOException e) {
            log.error("Convert image fail, image id = {}", imageId);
            return null;
        }
    }

    private String getImagePath(String fileName) {
        String currentProjectPath = System.getProperty("user.dir");
        return currentProjectPath + File.separator + "src/main/resources/static/assets/product"
                + File.separator + fileName;
    }


    // convert file to byte array
    private byte[] convert(String imagePath) throws IOException {
        // Create a FileInputStream object to read the image file.
        FileInputStream fis = new FileInputStream(imagePath);

        // Create a ByteArrayOutputStream object to store the image data in bytes.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // Read the image data from the FileInputStream object and write it to the ByteArrayOutputStream object.
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        // Close the FileInputStream and ByteArrayOutputStream objects.
        fis.close();
        bos.close();

        // Get the byte array containing the image data from the ByteArrayOutputStream object.
        byte[] imageData = bos.toByteArray();
        return imageData;
    }


    @Override
    public Image detail(Integer id) {
        Optional<Image> optional = imageRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public byte[] getImageByProductId(Integer productId) {
        var image = imageRepository.findTopByProductIdAndStatusOrderByUpdateDateDesc(productId, 1);
        if (image == null) {
            log.info("productId id = {} is not contain image", productId);
            return null;
        }
        try {
            return convert(getImagePath(image.getName()));
        } catch (IOException e) {
            log.error("Convert image fail, productId = {}", productId);
            return null;
        }
    }

    @Override
    public byte[] getImageDeletedByProductId(Integer productId) {
        var image = imageRepository.findTopByProductIdAndStatusOrderByUpdateDateDesc(productId, 0);
        if (image == null) {
            log.info("productId id = {} is not contain image", productId);
            return null;
        }
        try {
            return convert(getImagePath(image.getName()));
        } catch (IOException e) {
            log.error("Convert image fail, productId = {}", productId);
            return null;
        }
    }

    @Override
    public Page<Image> getAllDeletedByProductId(Integer productId ,int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return imageRepository.findAllByProductIdAndStatusOrderByUpdateDateDesc(productId, 0, pageable);
    }

    @Override
    public Image restore(Integer id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        if (optionalImage.isPresent()) {
            Image restoreImage = optionalImage.get();
            restoreImage.setStatus(1);
            restoreImage.setUpdateDate(new Date());
            return imageRepository.save(restoreImage);
        } else {
            return null;
        }
    }

    @Override
    public List<Image> getAllImageByProductId(Integer productId) {
        return imageRepository.findAllByProduct_IdAndStatusOrderByUpdateDateDesc(productId, 1);
    }

    @Override
    public Image getImageActiveByProductId(Integer productId) {
        return imageRepository.findTopByProduct_IdAndStatusOrderByUpdateDateDesc(productId, 1);
    }
}
