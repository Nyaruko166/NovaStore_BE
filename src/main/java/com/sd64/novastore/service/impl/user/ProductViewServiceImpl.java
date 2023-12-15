package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.dto.common.impl.ProductHomeDtoImpl;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ColorRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.repository.user.ProductViewRepository;
import com.sd64.novastore.repository.user.UserSizeRepository;
import com.sd64.novastore.response.ColorDetailResponse;
import com.sd64.novastore.response.ProductHomeResponse;
import com.sd64.novastore.response.PropertiesResponse;
import com.sd64.novastore.response.SizeDetailResponse;
import com.sd64.novastore.service.user.ProductViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductViewServiceImpl implements ProductViewService {
    @Autowired
    private ProductViewRepository productViewRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private UserSizeRepository userSizeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProductView() {
        return productViewRepository.getAllProductResponse();
    }


    @Override
    public Product getOne(Integer id) {
        Optional<Product> optional = productViewRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    @Override
    public Page<ProductHomeResponse> getAllProductShopResponse(int page) {
        List<ProductHomeDtoImpl> productHomeResponseDtoList = productViewRepository.getAllProductResponseHome()
                .stream().map(ProductHomeDtoImpl::toData).toList();
        List<ProductHomeResponse> productHomeResponses = new ArrayList<>();
        for (int index = 0; index < productHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            var prdResponse = productHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdResponse == null) {
                prdResponse = productHomeResponseDtoList.get(finalIndex).toResponse();
                productHomeResponses.add(prdResponse);
            } else {
                int i = productHomeResponses.indexOf(prdResponse);
                productHomeResponses.get(i).comparePrice(productHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        Pageable pageable = PageRequest.of(page, 9);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productHomeResponses.size());
        List<ProductHomeResponse> pageContent = productHomeResponses.subList(start, end);
        return new PageImpl<>(pageContent, pageable, productHomeResponses.size());
    }

    @Override
    public Page<ProductHomeResponse> getAllProductHomeResponse(int page) {
        List<ProductHomeDtoImpl> productHomeResponseDtoList = productViewRepository.getAllProductResponseHome()
                .stream().map(ProductHomeDtoImpl::toData).toList();
        List<ProductHomeResponse> productHomeResponses = new ArrayList<>();
        for (int index = 0; index < productHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            var prdResponse = productHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdResponse == null) {
                prdResponse = productHomeResponseDtoList.get(finalIndex).toResponse();
                productHomeResponses.add(prdResponse);
            } else {
                int i = productHomeResponses.indexOf(prdResponse);
                productHomeResponses.get(i).comparePrice(productHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        Pageable pageable = PageRequest.of(page, 9);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productHomeResponses.size());
        List<ProductHomeResponse> pageContent = productHomeResponses.subList(start, end);
        return new PageImpl<>(pageContent, pageable, productHomeResponses.size());
    }

    @Override
    public List<ProductHomeResponse> getAllProductHomeResponse() {
        List<ProductHomeDtoImpl> productHomeResponseDtoList = productViewRepository.getAllProductResponseHome()
                .stream().map(e -> ProductHomeDtoImpl.toData(e)).collect(Collectors.toList());
        List<ProductHomeResponse> productHomeResponses = new ArrayList<>();
        for (int index = 0; index < productHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            var prdResponse = productHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdResponse == null) {
                prdResponse = productHomeResponseDtoList.get(finalIndex).toResponse();
                productHomeResponses.add(prdResponse);
            } else {
                int i = productHomeResponses.indexOf(prdResponse);
                productHomeResponses.get(i).comparePrice(productHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        List<ProductHomeResponse> listProductResponse = new ArrayList<>();
        int index = 1;
        for (ProductHomeResponse productHomeResponse: productHomeResponses) {
            if (index <= 8) {
                listProductResponse.add(productHomeResponse);
                index++;
            }
        }
        return listProductResponse;
    }

    @Override
    public List<ProductHomeResponse> getAllProductDiscountHomeResponse() {
        List<ProductHomeDtoImpl> productHomeResponseDtoList = productViewRepository.getAllProductDiscountResponseHome()
                .stream().map(ProductHomeDtoImpl::toData).toList();
        List<ProductHomeResponse> productHomeResponses = new ArrayList<>();
        for (int index = 0; index < productHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            var prdResponse = productHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdResponse == null) {
                prdResponse = productHomeResponseDtoList.get(finalIndex).toResponse();
                productHomeResponses.add(prdResponse);
            } else {
                int i = productHomeResponses.indexOf(prdResponse);
                productHomeResponses.get(i).comparePrice(productHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        List<ProductHomeResponse> listProductResponse = new ArrayList<>();
        int index = 1;
        for (ProductHomeResponse productHomeResponse: productHomeResponses) {
            if (index <= 8) {
                listProductResponse.add(productHomeResponse);
                index++;
            }
        }
        return listProductResponse;
    }

//    public void getProductHome() {
//        List<ProductHomeDto> listProductHomeResponseDto = productViewRepository.getAllProductResponseHome();
//        ProductHomeDtoImpl productHomeResponseDto = new ProductHomeDtoImpl();
//        List<ProductHomeDtoImpl> listProductHomeResponseDtoImpl = new ArrayList<>();
//        for (int i = 0; i < listProductHomeResponseDto.size(); i++) {
//            productHomeResponseDto.setProductId(listProductHomeResponseDto.get(i).getProductId());
//            productHomeResponseDto.setProductName(listProductHomeResponseDto.get(i).getProductName());
//            productHomeResponseDto.setPrice(listProductHomeResponseDto.get(i).getPrice());
//            listProductHomeResponseDtoImpl.add(productHomeResponseDto);
//        }
//    }

    @Override
    public List<SizeDetailResponse> getAllSizeDetailResponse(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.findAllByProductIdAndStatusOrderByUpdateDateDesc(productId, 1);
        List<Size> listSize = userSizeRepository.getSizeByProductId(productId);
        List<SizeDetailResponse> listSizeDetailResponse = new ArrayList<>();
//        SizeDetailResponse sizeDetailResponse = new SizeDetailResponse();
        for (int i = 0; i < listProductDetail.size(); i++) {
            var size = listProductDetail.get(i).getSize();
            int colorId = listProductDetail.get(i).getColor().getId();
            var sizeDetailResponse = listSizeDetailResponse.stream().filter(el -> el.getId() == size.getId())
                    .findFirst().orElse(null);
            if (sizeDetailResponse == null) {
                sizeDetailResponse = new SizeDetailResponse();
                sizeDetailResponse.setId(size.getId());
                sizeDetailResponse.setName(size.getName());
                List<Integer> listColorId = new ArrayList<>();
                listColorId.add(colorId);
                List<PropertiesResponse> propertiesResponseList = new ArrayList<>();
                sizeDetailResponse.setListColorId(listColorId);
                PropertiesResponse propertiesResponse = new PropertiesResponse();
                propertiesResponse.setCode(listProductDetail.get(i).getCode());
                propertiesResponse.setPrice(listProductDetail.get(i).getPrice());
                propertiesResponse.setQuantity(listProductDetail.get(i).getQuantity());
                propertiesResponse.setId(colorId);
                propertiesResponseList.add(propertiesResponse);
                sizeDetailResponse.setPropertiesResponseList(propertiesResponseList);
                listSizeDetailResponse.add(sizeDetailResponse);
            } else {
                int index = listSizeDetailResponse.indexOf(sizeDetailResponse);
                if (!listSizeDetailResponse.get(index).getListColorId().contains(colorId)) {
                    listSizeDetailResponse.get(index).getListColorId().add(colorId);
                    PropertiesResponse propertiesResponse = new PropertiesResponse();
                    propertiesResponse.setCode(listProductDetail.get(i).getCode());
                    propertiesResponse.setPrice(listProductDetail.get(i).getPrice());
                    propertiesResponse.setQuantity(listProductDetail.get(i).getQuantity());
                    propertiesResponse.setId(colorId);
                    listSizeDetailResponse.get(index).getPropertiesResponseList().add(propertiesResponse);
                }
            }
        }
        return listSizeDetailResponse;
    }

    @Override
    public List<ColorDetailResponse> getAllColorDetailResponse(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.findAllByProductIdAndStatusOrderByUpdateDateDesc(productId, 1);
        List<ColorDetailResponse> listColorDetailResponses = new ArrayList<>();
        for (int i = 0; i < listProductDetail.size(); i++) {
            var color = listProductDetail.get(i).getColor();
            int sizeId = listProductDetail.get(i).getSize().getId();
            var colorDetailResponse = listColorDetailResponses.stream().filter(el -> el.getId() == color.getId())
                    .findFirst().orElse(null);
            if (colorDetailResponse == null) {
                colorDetailResponse = new ColorDetailResponse();
                colorDetailResponse.setId(color.getId());
                colorDetailResponse.setName(color.getName());
                List<Integer> listSize = new ArrayList<>();
                listSize.add(sizeId);
                colorDetailResponse.setListSizeId(listSize);
                listColorDetailResponses.add(colorDetailResponse);
            } else {
                int index = listColorDetailResponses.indexOf(colorDetailResponse);
                if (!listColorDetailResponses.get(index).getListSizeId().contains(sizeId)) {
                    listColorDetailResponses.get(index).getListSizeId().add(sizeId);
                }
            }
        }
        return listColorDetailResponses;
    }


    @Override
    public Page<ProductHomeResponse> getAllProductYouMayLike(int page) {
        List<ProductHomeDtoImpl> productHomeResponseDtoList = productViewRepository.getAllProductYouMayLike()
                .stream().map(e -> ProductHomeDtoImpl.toData(e)).collect(Collectors.toList());
        List<ProductHomeResponse> productYouMayLikeResponse = new ArrayList<>();
        for (int index = 0; index < productHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            var prdResponse = productYouMayLikeResponse.stream()
                    .filter(e -> e.getId().intValue() == productHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdResponse == null) {
                prdResponse = productHomeResponseDtoList.get(finalIndex).toResponse();
                productYouMayLikeResponse.add(prdResponse);
            } else {
                int i = productYouMayLikeResponse.indexOf(prdResponse);
                productYouMayLikeResponse.get(i).comparePrice(productHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        Pageable pageable = PageRequest.of(page, 5);
        long totalElements = productYouMayLikeResponse.stream().count();
        return new PageImpl(productYouMayLikeResponse, pageable, totalElements);
    }

    @Override
    public BigDecimal getPriceMaxResponseByProductId(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.getAllProductDetailByProductIdOrderByPriceDesc(productId);
        BigDecimal priceMax = listProductDetail.get(0).getPrice();
        return priceMax;
    }

    @Override
    public BigDecimal getPriceMinResponseByProductId(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.getAllProductDetailByProductIdOrderByPriceAsc(productId);
        BigDecimal priceMin = listProductDetail.get(0).getPrice();
        return priceMin;
    }

    @Override
    public BigDecimal getPriceProductDetailResponseByProductIdAndSizeIdAndColorId(Integer productId, Integer sizeId, Integer colorId) {
        ProductDetail productDetail = productViewRepository.getProductDetailByProductIdAndSizeIdAndColorId(productId, sizeId, colorId);
        return productDetail.getPrice();
    }

    @Override
    public Page<ProductHomeResponse> searchProductShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, int page) {
        List<ProductHomeDtoImpl> productHomeResponseDtoList = productViewRepository.searchProductResponse(brandId, categoryId, formId, materialId, productNameSearch, priceMax, priceMin)
        .stream().map(e -> ProductHomeDtoImpl.toData(e)).collect(Collectors.toList());
        List<ProductHomeResponse> productHomeResponses = new ArrayList<>();
        for (int index = 0; index < productHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            var prdResponse = productHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdResponse == null) {
                prdResponse = productHomeResponseDtoList.get(finalIndex).toResponse();
                productHomeResponses.add(prdResponse);
            } else {
                int i = productHomeResponses.indexOf(prdResponse);
                productHomeResponses.get(i).comparePrice(productHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        Pageable pageable = PageRequest.of(page, 9);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), productHomeResponses.size());
        List<ProductHomeResponse> pageContent = productHomeResponses.subList(start, end);
        return new PageImpl<>(pageContent, pageable, productHomeResponses.size());
    }

    @Override
    public BigDecimal getPriceMaxBySelected(Integer price) {
        if (price == null) {
            return BigDecimal.valueOf(Integer.MAX_VALUE);
        } else if (price == 1) {
            return BigDecimal.valueOf(100000);
        } else if (price == 2) {
            return BigDecimal.valueOf(500000);
        } else if (price == 3) {
            return BigDecimal.valueOf(1000000);
        } else if (price == 4) {
            return BigDecimal.valueOf(5000000);
        } else if (price == 5) {
            return BigDecimal.valueOf(10000000);
        } else if (price == 6) {
            return BigDecimal.valueOf(50000000);
        } else if (price == 7) {
            return BigDecimal.valueOf(100000000);
        } else if (price == 8) {
            return BigDecimal.valueOf(500000000);
        } else if (price == 9) {
            return BigDecimal.valueOf(Integer.MAX_VALUE);
        }
        return null;
    }

    @Override
    public BigDecimal getPriceMinBySelected(Integer price) {
        if (price == null) {
            return BigDecimal.valueOf(0);
        } else if (price == 1) {
            return BigDecimal.valueOf(0);
        } else if (price == 2) {
            return BigDecimal.valueOf(100000);
        } else if (price == 3) {
            return BigDecimal.valueOf(500000);
        } else if (price == 4) {
            return BigDecimal.valueOf(1000000);
        } else if (price == 5) {
            return BigDecimal.valueOf(5000000);
        } else if (price == 6) {
            return BigDecimal.valueOf(10000000);
        } else if (price == 7) {
            return BigDecimal.valueOf(50000000);
        } else if (price == 8) {
            return BigDecimal.valueOf(100000000);
        } else if (price == 9) {
            return BigDecimal.valueOf(500000000);
        }
        return null;
    }
}
