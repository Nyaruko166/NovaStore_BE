package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.Impl.ProductDetailDtoImpl;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.response.ProductDetailSearchResponse;
import com.sd64.novastore.service.ProductDetailService;
import com.sd64.novastore.utils.FileUtil;
import com.sd64.novastore.utils.productdetail.ProductDetailExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

    @Override
    public Boolean add(Integer productId, Integer quantity, BigDecimal price, Integer sizeId, Integer colorId) {
        ProductDetail productDetail = new ProductDetail();
        productDetail.setStatus(1);
        productDetail.setCreateDate(new java.util.Date());
        productDetail.setUpdateDate(new java.util.Date());
        productDetail.setProduct(Product.builder().id(productId).build());
        productDetail.setQuantity(quantity);
        productDetail.setPrice(price);
        productDetail.setSize(Size.builder().id(sizeId).build());
        productDetail.setColor(Color.builder().id(colorId).build());
        productDetailRepository.save(productDetail);
        productDetail.setCode("CT"+productDetail.getId());
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
            optional.get().setStatus(0);
            optional.get().setUpdateDate(new Date());
            return productDetailRepository.save(optional.get());
        } else {
            return null;
        }
    }

    @Override
    public ProductDetail getOne(Integer id) {
        return productDetailRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProductDetail> getProductDetailByProductId(int page, Integer productId) {
        Pageable pageable = PageRequest.of(page, 5);
        return productDetailRepository.getAllProductDetailByProduct_IdAndStatusOrderByUpdateDateDesc(pageable, productId, 1);
    }

    @Override
    public List<ProductDetail> getProductDetailByProductId(Integer productId) {
        return null;
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
    public Page<ProductDetailSearchResponse> getProductByPriceAndSizeIdAndColorId(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 5);
        var pageProductDetailDto = productDetailRepository.getProductByPriceAndSizeIdAndColorId(productId, priceMin, priceMax, sizeId, colorId, pageable)
                .stream().map(ProductDetailDtoImpl::toProductSearchResponse).collect(Collectors.toList());
        long totalElements = productDetailRepository.getProductByPriceAndSizeIdAndColorId(productId, priceMin, priceMax, sizeId, colorId).stream().count();
        return new PageImpl(pageProductDetailDto, pageable, totalElements);
    }

    public int calculateTotalPages(int totalElemets, int elementsPerpage) {
        int totalPage = totalElemets / elementsPerpage;
        if (totalElemets % elementsPerpage != 0) {
            totalPage++;
        }
        return totalPage;
    }

    @Override
    public int getTotalPage(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 5);
        var pageProductDetailDto = productDetailRepository.getProductByPriceAndSizeIdAndColorId(productId, priceMin, priceMax, sizeId, colorId, pageable)
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
    public Page<ProductDetailSearchResponse> getProductByPriceAndSizeIdAndColorIdDeleted(int page, Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId) {
        Pageable pageable = PageRequest.of(page, 5);
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
    public List<BigDecimal> getPriceActiveProductDetailByProductId(Integer productId) {
        return null;
    }

    @Override
    public BigDecimal getPriceMaxByProductId(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.findByProductIdAndStatusOrderByPriceDesc(productId, 1);
        return listProductDetail.get(0).getPrice();
    }

    @Override
    public BigDecimal getPriceMinByProductId(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.findByProductIdAndStatusOrderByPriceAsc(productId, 1);
        return listProductDetail.get(0).getPrice();
    }
}
