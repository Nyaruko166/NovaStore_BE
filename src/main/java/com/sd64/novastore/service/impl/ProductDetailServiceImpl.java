package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.Impl.ProductDetailDtoImpl;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.response.ProductDetailSearchResponse;
import com.sd64.novastore.response.ProductDiscountHomeResponse;
import com.sd64.novastore.response.ProductHomeResponse;
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.QRCodeUtil;
import com.sd64.novastore.utils.productdetail.ProductDetailExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailExcelUtil productDetailExcelUtil;


    @Override
    public Page<ProductDetail> getAllPT(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productDetailRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

//    public Boolean checkColorAndSize(String colorName, String sizeName) {
//
//    }

    @Override
    public List<ProductDetail> getProductDetailNoDeleteResponse(List<ProductDetail> listProductDetail) {
        List<ProductDetail> listProductDetailResponse = new ArrayList<>();
        for (ProductDetail productDetail : listProductDetail) {
            if (productDetail.getStatus() == 1) {
                listProductDetailResponse.add(productDetail);
            }
        }
        return listProductDetailResponse;
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

    public String formatName(String name) {
        // Loại bỏ dấu cách đầu tiên
        name = name.replaceFirst("^\\s+", "");

        // Loại bỏ các dấu cách khi có hai dấu cách trở lên liền nhau
        name = name.replaceAll("\\s{2,}", " ");
        return name;
    }

    @Override
    public Boolean add(Integer productId, Integer quantity, BigDecimal price, Integer sizeId, Integer colorId) throws IOException {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setStatus(1);
        productDetail.setCreateDate(new java.util.Date());
        productDetail.setUpdateDate(new java.util.Date());
        productDetail.setProduct(Product.builder().id(productId).build());
        productDetail.setQuantity(quantity);
        productDetail.setPrice(price);
        productDetail.setPriceDiscount(price);
        productDetail.setSize(Size.builder().id(sizeId).build());
        productDetail.setColor(Color.builder().id(colorId).build());
        productDetail.setCode(generateProductDetailCode(1));
        QRCodeUtil.generateQRCode(productDetail.getCode(), productDetail.getCode());
        productDetailRepository.save(productDetail);
        return true;
    }

    @Override
    public ProductDetail update(Integer id, Integer productId, Integer quantity, BigDecimal price, Integer sizeId, Integer colorId) {
        Optional<ProductDetail> productDetailOptional = productDetailRepository.findById(id);
        if (productDetailOptional.isPresent()) {
            ProductDetail productDetail = productDetailOptional.get();
            productDetail.setId(id);
            productDetail.setStatus(productDetail.getStatus());
            productDetail.setCreateDate(productDetail.getCreateDate());
            productDetail.setQuantity(quantity);
            productDetail.setPrice(price);
            productDetail.setPriceDiscount(price);
            productDetail.setUpdateDate(new Date());
            productDetail.setProduct(Product.builder().id(productId).build());
            productDetail.setColor(Color.builder().id(colorId).build());
            productDetail.setSize(Size.builder().id(sizeId).build());
            return productDetailRepository.save(productDetail);
        }
        return null;
    }

    @Override
    public ProductDetail delete(Integer id) {
        Optional<ProductDetail> optional = productDetailRepository.findById(id);
        if (optional.isPresent()) {
            ProductDetail productDetail = optional.get();
            productDetail.setStatus(0);
            productDetail.setUpdateDate(new Date());
            return productDetailRepository.save(productDetail);
        } else {
            return null;
        }
    }

    @Override
    public ProductDetail getOne(Integer id) {
        return productDetailRepository.findById(id).orElse(null);
    }

    @Override
    public String importExcelProduct(MultipartFile file, Integer productId) throws IOException {
        if (productDetailExcelUtil.isValidExcel(file)) {
            String uploadDir = "./src/main/resources/static/filecustom/productdetail/";
            String fileName = file.getOriginalFilename();
            String excelPath = FileUtil.copyFile(file, fileName, uploadDir);

            String status = productDetailExcelUtil.getProductDetailFromExcel(excelPath, productId);
            if (status.contains("Trùng mã")) {
                return "Trùng mã";
            } else if (status.contains("Sai dữ liệu")) {
                return "Sai dữ liệu";
            } else {
                return "Oke bạn ơi";
            }
        } else {
            return "lỗi file";
        }
    }

    @Override
    public Page<ProductDetailSearchResponse> getProductByPriceAndSizeIdAndColorId(int page, Integer productId, BigDecimal priceMinDiscount, BigDecimal priceMaxDiscount, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 10);
        var pageProductDetailDto = productDetailRepository.getProductDetailByPriceAndSizeIdAndColorId(productId, priceMinDiscount, priceMaxDiscount, sizeId, colorId)
                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), pageProductDetailDto.size());
        List<ProductDetailSearchResponse> pageContent = pageProductDetailDto.subList(start, end);
        return new PageImpl<>(pageContent, pageable, pageProductDetailDto.size());
    }

    public int calculateTotalPages(int totalElemets, int elementsPerpage) {
        int totalPage = totalElemets / elementsPerpage;
        if (totalElemets % elementsPerpage != 0) {
            totalPage++;
        }
        return totalPage;
    }

//    @Override
//    public int getTotalPage(int page, Integer productId, BigDecimal priceMinDiscount, BigDecimal priceMaxDiscount, Integer sizeId, Integer colorId) {
//        Pageable pageable = PageRequest.of(page, 5);
//        var pageProductDetailDto = productDetailRepository.getProductByPriceAndSizeIdAndColorId(productId, priceMinDiscount, priceMaxDiscount, sizeId, colorId, pageable)
//                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
//        int totalElemets = pageProductDetailDto.size();
//        int elementsPerpage = 5;
//        int totalPage = totalElemets / elementsPerpage;
//        if (totalElemets % elementsPerpage != 0) {
//            totalPage++;
//        }
//        return totalPage;
//    }

    @Override
    public Page<ProductDetailSearchResponse> getProductByPriceAndSizeIdAndColorIdDeleted(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 10);
        var pageProductDetailDto = productDetailRepository.getProductByPriceAndSizeIdAndColorIdDeleted(productId, priceMin, priceMax, sizeId, colorId, pageable)
                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
        long totalElements = productDetailRepository.getProductByPriceAndSizeIdAndColorIdDeleted(productId, priceMin, priceMax, sizeId, colorId).stream().count();
        return new PageImpl(pageProductDetailDto, pageable, totalElements);
    }

    @Override
    public ProductDetail restore(Integer id) {
        Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(id);
        if (optionalProductDetail.isPresent()) {
            ProductDetail restoreProductDetail = optionalProductDetail.get();
            restoreProductDetail.setStatus(1);
            restoreProductDetail.setUpdateDate(new Date());
            return productDetailRepository.save(restoreProductDetail);
        } else {
            return null;
        }
    }

    @Override
    public int getTotalPageDeleted(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 5);
        var pageProductDetailDto = productDetailRepository.getProductByPriceAndSizeIdAndColorIdDeleted(productId, priceMin, priceMax, sizeId, colorId, pageable)
                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
        int totalElemets = pageProductDetailDto.size();
        int elementsPerpage = 5;
        int totalPage = totalElemets / elementsPerpage;
        if (totalElemets % elementsPerpage != 0) {
            totalPage++;
        }
        return totalPage;
    }

    @Override
    public byte[] getProductDetail(Integer id) {
        var productDetail = productDetailRepository.findByIdAndStatus(id, 1);
        if (productDetail == null) {
            log.info("ProductDetailId = {} is not exist on DB", id);
            return null;
        }
        try {
            return convert(getQRPath(productDetail.getCode()));
        } catch (IOException e) {
            log.error("Convert qrCode fail, ProductDetailId = {}", id);
            return null;
        }
    }

    private byte[] convert(String qrCodePath) throws IOException {
        // Create a FileInputStream object to read the image file.
        qrCodePath = qrCodePath + ".png";
        FileInputStream fis = new FileInputStream(qrCodePath);

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
        byte[] qrData = bos.toByteArray();
        return qrData;
    }

    private String getQRPath(String fileName) {
        String currentProjectPath = System.getProperty("user.dir");
        return currentProjectPath + File.separator + "src/main/resources/static/assets/qrcode"
                + File.separator + fileName;
    }

    @Override
    public Page<ProductDetail> findAllByProductNameAndStatus(String name, Integer status, Integer qty, Pageable pageable) {
        return productDetailRepository.searchAllByProductNameAndStatus(name, status, qty, pageable);
    }

    @Override
    public BigInteger getTotalQuantityProductDetail() {
        return productDetailRepository.getTotalQuantity(1);
    }

    @Override
    public BigInteger getTotalQuantityByProductId(Integer productId) {
        return productDetailRepository.getTotalQuantityById(1, productId);
    }

    @Override
    public List<ProductDetail> findAllActive() {
        return productDetailRepository.findAllByStatusOrderByIdDesc(1);
    }
}
