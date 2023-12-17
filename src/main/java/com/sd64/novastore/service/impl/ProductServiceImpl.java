package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.model.*;
import com.sd64.novastore.repository.ImageRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.service.ImageService;
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.service.ProductService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.QRCodeUtil;
import com.sd64.novastore.utils.product.ProductExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductExcelUtil productExcelUtil;


    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductDetailService productDetailService;


    @Override
    public List<Product> getAll() {
        return null;
    }

    @Override
    public Page<Product> getAll(Integer page) {
        return null;
    }

    @Override
    public Page<ProductDto> getAll(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.getAllProduct(pageable);
    }

    public String generateProductCode() {
        Product productFinalPresent = productRepository.findTopByOrderByIdDesc();
        if (productFinalPresent == null) {
            return "SP00001";
        }
        Integer idFinalPresent = productFinalPresent.getId() + 1;
        String code = String.format("%05d", idFinalPresent);
        return "SP" + code;
    }

    public String generateProductDetailCode(int count) {
        ProductDetail productDetailFinalPresent = productDetailRepository.findTopByOrderByIdDesc();
        if (productDetailFinalPresent == null) {
            return "CT00001";
        }
        Integer idFinalPresent = productDetailFinalPresent.getId() + count;
        String code = String.format("%05d", idFinalPresent);
        return "CT" + code;
    }

    @Override
    public void addFinal(Product productAdd, List<ProductDetail> listProductDetailAdd, List<MultipartFile> filesAdd) throws IOException {
        List<ProductDetail> listProductDetail = new ArrayList<>();
        int count = 1;
        for (ProductDetail productDetail : listProductDetailAdd) {
            productDetail.setProduct(productAdd);
            productDetail.setCode(generateProductCode());
            productDetail.setCreateDate(new Date());
            productDetail.setUpdateDate(new Date());
            productDetail.setStatus(1);
            productDetail.setCode(generateProductDetailCode(count));
            productDetail.setPriceDiscount(productDetail.getPrice());
            listProductDetail.add(productDetail);
            count++;
        }
        productAdd.setListProductDetail(listProductDetail);
        productAdd.setCode(generateProductCode());
        List<Image> listImage = new ArrayList<>();
        String uploadDir = "./src/main/resources/static/assets/product/";
        for (MultipartFile file : filesAdd) {
            String fileName = file.getOriginalFilename();
            String uid = "product_" + new Date().getTime();
            String avtPath = FileUtil.copyFile(file, fileName, uploadDir);
            String imageUrl = FileUtil.rename(avtPath, uid);
            listImage.add(new Image(null, imageUrl, new Date(), new Date(), 1, productAdd));
        }
        productAdd.setListImage(listImage);
        for (ProductDetail productDetail : productAdd.getListProductDetail()) {
            QRCodeUtil.generateQRCode(productDetail.getCode(), productDetail.getCode());
        }
        productAdd.setCreateDate(new Date());
        productAdd.setUpdateDate(new Date());
        productAdd.setStatus(1);
        productRepository.save(productAdd);
    }

    @Override
    public void updateFinal(Product productBefore, Product productUpdate, List<ProductDetail> listProductDetailUpdate,
                            List<MultipartFile> filesUpdate, List<Integer> imageRemoveIds, List<Integer> productDetailRemoveIds
    ) throws IOException {
        List<Image> listImage = imageService.getAllImageByProductIdNoStatus(productBefore.getId());
        if (filesUpdate != null) {
            String uploadDir = "./src/main/resources/static/assets/product/";
            for (MultipartFile file : filesUpdate) {
                String fileName = file.getOriginalFilename();
                String uid = "product_" + new Date().getTime();
                String avtPath = FileUtil.copyFile(file, fileName, uploadDir);
                String imageUrl = FileUtil.rename(avtPath, uid);
                listImage.add(new Image(null, imageUrl, new Date(), new Date(), 1, productUpdate));
            }
        }
        if (!imageRemoveIds.isEmpty()) {
            for (Integer items : imageRemoveIds) {
                imageService.delete(items);
            }
        }

//        if (!productDetailRemoveIds.isEmpty()) {
//            for (Integer items : productDetailRemoveIds) {
//                productDetailService.delete(items);
//            }
//        }

        // List hiển thị tất cả phần tử không quan tâm trang thái
        List<ProductDetail> listProductDetailBefore = productDetailRepository.findAllByProduct_IdOrderByIdAsc(productBefore.getId());
        for (ProductDetail productDetail : listProductDetailBefore) {
            productDetail.setProduct(productUpdate);
        }
        for (ProductDetail productDetail : listProductDetailUpdate) {
            productDetail.setProduct(productUpdate);
        }
//        productBefore.setListProductDetail(listProductDetailUpdate);
//        productUpdate.setListProductDetail(listProductDetailUpdate);
        int index = 0;

        // List hiển thị tất cả các phần tử trang thái 1
        List<ProductDetail> listProductDetailNoDelete = productDetailService.getProductDetailNoDeleteResponse(listProductDetailBefore);
        for (int i = 0; i < listProductDetailNoDelete.size(); i++) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setId(listProductDetailBefore.get(i).getId());
            productDetail.setCode(listProductDetailBefore.get(i).getCode());
            productDetail.setCreateDate(listProductDetailBefore.get(i).getCreateDate());
            productDetail.setUpdateDate(new Date());
            productDetail.setStatus(listProductDetailBefore.get(i).getStatus());
            productDetail.setProduct(productUpdate);
            productDetail.setPriceDiscount(listProductDetailNoDelete.get(i).getPriceDiscount());
            productDetail.setQuantity(listProductDetailUpdate.get(i).getQuantity());
            productDetail.setPrice(listProductDetailUpdate.get(i).getPrice());
            productDetail.setSize(listProductDetailUpdate.get(i).getSize());
            productDetail.setColor(listProductDetailUpdate.get(i).getColor());
            listProductDetailUpdate.set(i, productDetail);
            QRCodeUtil.generateQRCode(listProductDetailUpdate.get(i).getCode(), listProductDetailUpdate.get(i).getCode());
            index++;
        }
        int count = 1;
        for (int i = index; i < listProductDetailUpdate.size(); i++) {
            ProductDetail productDetail = new ProductDetail();
            productDetail.setProduct(productUpdate);
            productDetail.setCreateDate(new Date());
            productDetail.setUpdateDate(new Date());
            productDetail.setStatus(productUpdate.getStatus());
            productDetail.setCode(generateProductDetailCode(count));
            productDetail.setQuantity(listProductDetailUpdate.get(i).getQuantity());
            productDetail.setPrice(listProductDetailUpdate.get(i).getPrice());
            productDetail.setSize(listProductDetailUpdate.get(i).getSize());
            productDetail.setColor(listProductDetailUpdate.get(i).getColor());
            listProductDetailUpdate.set(i, productDetail);
            QRCodeUtil.generateQRCode(listProductDetailUpdate.get(i).getCode(), listProductDetailUpdate.get(i).getCode());
            count++;
        }
        listProductDetailUpdate = listProductDetailUpdate.stream().map(item -> {
            if (productDetailRemoveIds.contains(item.getId())) {
                item.setStatus(0);
            }
            return item;
        }).collect(Collectors.toList());
        productUpdate.setListImage(listImage);
        productUpdate.setCreateDate(productBefore.getCreateDate());
        productUpdate.setStatus(productBefore.getStatus());
        productUpdate.setUpdateDate(new Date());
        productUpdate.setListProductDetail(listProductDetailUpdate);
        productRepository.save(productUpdate);
    }

    @Override
    public void update(Product productUpdate, Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productUpdate.getName());
            product.setStatus(productUpdate.getStatus());
            product.setDescription(productUpdate.getDescription());
            product.setCreateDate(productUpdate.getCreateDate());
            product.setUpdateDate(new Date());
            product.setMaterial(productUpdate.getMaterial());
            product.setCategory(product.getCategory());
            product.setBrand(productUpdate.getBrand());
            product.setForm(productUpdate.getForm());
            product.setCode(productUpdate.getCode());
            product.setId(productId);
            productRepository.save(product);
        }


    }

    @Override
    public Boolean add(String productName, String description, Integer materialId, Integer categoryId, Integer brandId, Integer formId) {
        Product product = new Product();
        product.setName(productName);
        product.setStatus(1);
        product.setDescription(description);
        product.setCreateDate(new Date());
        product.setUpdateDate(new Date());
        product.setMaterial(Material.builder().id(materialId).build());
        product.setCategory(Category.builder().id(categoryId).build());
        product.setBrand(Brand.builder().id(brandId).build());
        product.setForm(Form.builder().id(formId).build());
        product.setCode(generateProductCode());
        productRepository.save(product);
        return true;
    }

    @Override
    public Product update(Integer id, String code, String productName, String description, Integer materialId,
                          Integer categoryId, Integer brandId, Integer formId) {
        Product product = new Product();
        product.setId(id);
        product.setCode(code);
        product.setName(productName);
        product.setStatus(1);
        product.setDescription(description);
        product.setCreateDate(new Date());
        product.setUpdateDate(new Date());
        product.setMaterial(Material.builder().id(materialId).build());
        product.setCategory(Category.builder().id(categoryId).build());
        product.setBrand(Brand.builder().id(brandId).build());
        product.setForm(Form.builder().id(formId).build());
        return productRepository.save(product);
    }

    @Override
    public Product delete(Integer id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent()) {
            Product product = optional.get();
            product.setStatus(0);
            product.setUpdateDate(new Date());
            return productRepository.save(product);
        } else {
            return null;
        }
    }

    @Override
    public Page<ProductDto> search(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.search(pageable, brandId, categoryId, formId, materialId, productName.trim(), description.trim());
    }

    @Override
    public Product getOne(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Integer importExcelProduct(MultipartFile file) throws IOException {
        if (productExcelUtil.isValidExcel(file)) {
            String uploadDir = "./src/main/resources/static/filecustom/product/";
            String fileName = file.getOriginalFilename();
            String excelPath = FileUtil.copyFile(file, fileName, uploadDir);

            String status = productExcelUtil.getProductFromExcel(excelPath);
            if (status.contains("Sai dữ liệu")) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return 0; // Lỗi file
        }
    }

    @Override
    public Page<ProductDto> getAllProductDeleted(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.getAllProductDeleted(pageable);
    }

    @Override
    public Page<ProductDto> searchProductDeleted(Integer materialId, Integer brandId, Integer formId, Integer categoryId, String productName, String description, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        return productRepository.searchProductDeleted(pageable, brandId, categoryId, formId, materialId, productName.trim(), description.trim());
    }

    @Override
    public Product restore(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product restoreProduct = optionalProduct.get();
            restoreProduct.setStatus(1);
            restoreProduct.setUpdateDate(new Date());
            return productRepository.save(restoreProduct);
        } else {
            return null;
        }
    }

}
