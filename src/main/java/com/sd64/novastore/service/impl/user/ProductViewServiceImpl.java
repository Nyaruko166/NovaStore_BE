package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.dto.common.ProductAndValueDiscountDto;
import com.sd64.novastore.dto.common.ProductDetailAndValueDiscountDto;
import com.sd64.novastore.dto.common.impl.ProductDiscountHomeDtoImpl;
import com.sd64.novastore.dto.common.impl.ProductHomeDtoImpl;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ColorRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.ProductRepository;
import com.sd64.novastore.repository.user.ProductViewRepository;
import com.sd64.novastore.repository.user.UserSizeRepository;
import com.sd64.novastore.response.*;
import com.sd64.novastore.service.user.ProductViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
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
        return optional.orElse(null);
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
    public ProductDetailAndValueDiscountDto getProductDetailAndValueDiscount(Integer productDetailId) {
//        ProductDetailAndValueDiscountDto productDetailAndValueDiscountDto = productViewRepository.getProductDetailDto(productDetailId);
//        return productDetailAndValueDiscountDto;
        return null;
    }

    @Override
    public ProductAndValueDiscountDto getProductAndValueDiscount(Integer productId) {
        return productViewRepository.getProductDetailDto(productId);
    }


    @Override
    public Float getValueDiscountByProductId(Integer productId) {
        return productViewRepository.getValueDiscountByProductId(productId);
    }

    @Override
    public List<ProductDiscountHomeResponse> getRandomProductAndProductDiscount() {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.getAllProductAndDiscountResponseRandom()
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }

        return productDiscountHomeResponses;
    }

    @Override
    public BigDecimal calculatePriceToPriceDiscount(BigDecimal price, Float value) {
        if (value != null) {
            BigDecimal valueConvert = BigDecimal.valueOf(value);
            BigDecimal discount = price.multiply(valueConvert.divide(BigDecimal.valueOf(100)));
            BigDecimal finalPriceDiscount = price.subtract(discount);
            return finalPriceDiscount;
        } else {
            return price;
        }
    }

    @Override
    public List<ProductDiscountHomeResponse> setPriceDiscount(List<ProductDiscountHomeResponse> listProductDiscountHomeResponse) {
        for (int i = 0; i < listProductDiscountHomeResponse.size(); i++) {
            ProductDiscountHomeResponse productDiscountHomeResponse = new ProductDiscountHomeResponse();
            productDiscountHomeResponse.setId(listProductDiscountHomeResponse.get(i).getId());
            productDiscountHomeResponse.setValue(listProductDiscountHomeResponse.get(i).getValue());
            productDiscountHomeResponse.setName(listProductDiscountHomeResponse.get(i).getName());
            productDiscountHomeResponse.setPriceMax(listProductDiscountHomeResponse.get(i).getPriceMax());
            productDiscountHomeResponse.setPriceMin(listProductDiscountHomeResponse.get(i).getPriceMin());
            productDiscountHomeResponse.setPriceDiscountMax(calculatePriceToPriceDiscount(listProductDiscountHomeResponse.get(i).getPriceMax(), listProductDiscountHomeResponse.get(i).getValue()));
            productDiscountHomeResponse.setPriceDiscountMin(calculatePriceToPriceDiscount(listProductDiscountHomeResponse.get(i).getPriceMin(), listProductDiscountHomeResponse.get(i).getValue()));
            listProductDiscountHomeResponse.set(i, productDiscountHomeResponse);
        }
        return listProductDiscountHomeResponse;
    }


    @Override
    public List<ProductDiscountHomeResponse> getAllProductAndProductDiscountHomeResponse() {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.getAllProductAndDiscountResponseHome()
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
//                productDiscountHomeResponses.get(i).comparePriceDiscount(productDiscountHomeResponseDtoList.get(finalIndex).getPriceDiscount());
//                productDiscountHomeResponses.get(i).comparePriceDiscount(calculatePriceToPriceDiscount(productDiscountHomeResponseDtoList.get(finalIndex).getPrice(), productDiscountHomeResponseDtoList.get(finalIndex).getValue()));
            }
        }
        List<ProductDiscountHomeResponse> listProductDiscountResponse = new ArrayList<>();
        int index = 1;
        for (ProductDiscountHomeResponse producDiscounttHomeResponse: productDiscountHomeResponses) {
            if (index <= 8) {
                listProductDiscountResponse.add(producDiscounttHomeResponse);
                index++;
            }
        }
        return listProductDiscountResponse;
    }

    @Override
    public List<ProductDiscountHomeResponse> getAllProductDiscountHomeResponse() {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.getAllProductDiscountResponseHome()
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
//                productDiscountHomeResponses.get(i).comparePriceDiscount(calculatePriceToPriceDiscount(productDiscountHomeResponseDtoList.get(finalIndex).getPrice(), productDiscountHomeResponseDtoList.get(finalIndex).getValue()));
            }
        }
        List<ProductDiscountHomeResponse> listProductDiscountResponse = new ArrayList<>();
        int index = 1;
        for (ProductDiscountHomeResponse producDiscounttHomeResponse: productDiscountHomeResponses) {
            if (index <= 8) {
                listProductDiscountResponse.add(producDiscounttHomeResponse);
                index++;
            }
        }
        return listProductDiscountResponse;
    }


    @Override
    public List<ProductDiscountHomeResponse> getAllProductAndProductDiscountShopResponse() {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.getAllProductAndProductDiscountDiscountShopResponse()
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
//                productDiscountHomeResponses.get(i).comparePriceDiscount(calculatePriceToPriceDiscount(productDiscountHomeResponseDtoList.get(finalIndex).getPrice(), productDiscountHomeResponseDtoList.get(finalIndex).getValue()));
            }
        }
        return productDiscountHomeResponses;
    }

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
                propertiesResponse.setPriceDiscount(listProductDetail.get(i).getPriceDiscount());
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
                    propertiesResponse.setPriceDiscount(listProductDetail.get(i).getPriceDiscount());
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
    public BigDecimal getPriceDiscountMaxResponseByProductId(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.getAllProductDetailByProductIdOrderByPriceDiscountDesc(productId);
        BigDecimal priceMaxDiscount = listProductDetail.get(0).getPriceDiscount();
        return priceMaxDiscount;
    }

    @Override
    public BigDecimal getPriceDiscountMinResponseByProductId(Integer productId) {
        List<ProductDetail> listProductDetail = productDetailRepository.getAllProductDetailByProductIdOrderByPriceDiscountAsc(productId);
        BigDecimal priceMinDiscount = listProductDetail.get(0).getPriceDiscount();
        return priceMinDiscount;
    }


    public List<Integer> getStatusProductAndProductDiscount(Integer status) {
        List<Integer> listStatus = null;
        if (status == null) {
            listStatus = new ArrayList<>();
            listStatus.add(1);
            listStatus.add(2);
        } else if (status == 1) {
            listStatus = new ArrayList<>();
            listStatus.add(1);
        } else if (status == 2) {
            listStatus = new ArrayList<>();
            listStatus.add(2);
        }
        return listStatus;
    }

    @Override
    public List<ProductDiscountHomeResponse> searchProductAndProductDiscountShopResponse(
            List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId,
            List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax,
            BigDecimal priceMin, Integer status) {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.searchProductAndProductDiscountShopResponse(listBrandId, listCategoryId, listFormId, listMaterialId, listSizeId, listColorId, productNameSearch, priceMax, priceMin, getStatusProductAndProductDiscount(status))
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        return productDiscountHomeResponses;
    }

    @Override
    public List<ProductDiscountHomeResponse> searchOnlyProductDiscountShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin) {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.searchOnlyProductDiscountShopResponse(listBrandId, listCategoryId, listFormId, listMaterialId, listSizeId, listColorId, productNameSearch, priceMax, priceMin)
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        return productDiscountHomeResponses;
    }

    @Override
    public Page<ProductDiscountHomeResponse> convertlistToPage(List<ProductDiscountHomeResponse> listProductDiscount, int page) {
        Pageable pageable = PageRequest.of(page, 9);
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), listProductDiscount.size());
        List<ProductDiscountHomeResponse> pageContent = listProductDiscount.subList(start, end);
        return new PageImpl<>(pageContent, pageable, listProductDiscount.size());
    }

    @Override
    public List<ProductDiscountHomeResponse> searchProductAndProductDiscountDescShopResponse(
            List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId,
            List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax,
            BigDecimal priceMin, Integer status) {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.searchProductAndProductDiscountDescResponse(
                listBrandId, listCategoryId, listFormId, listMaterialId, listSizeId, listColorId, productNameSearch, priceMax, priceMin, getStatusProductAndProductDiscount(status))
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
            }
        }
        return productDiscountHomeResponses;
    }

    @Override
    public List<ProductDiscountHomeResponse> searchProductAndProductDiscountAscShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, Integer status) {
        List<ProductDiscountHomeDtoImpl> productDiscountHomeResponseDtoList = productViewRepository.searchProductAndProductDiscountAscResponse(listBrandId, listCategoryId, listFormId, listMaterialId, listSizeId, listColorId, productNameSearch, priceMax, priceMin, getStatusProductAndProductDiscount(status))
                .stream().map(ProductDiscountHomeDtoImpl::toData).toList();
        List<ProductDiscountHomeResponse> productDiscountHomeResponses = new ArrayList<>();
        for (int index = 0; index < productDiscountHomeResponseDtoList.size(); index++) {
            int finalIndex = index;
            ProductDiscountHomeResponse prdDiscountResponse = productDiscountHomeResponses.stream()
                    .filter(e -> e.getId().intValue() == productDiscountHomeResponseDtoList.get(finalIndex).getProductId().intValue())
                    .findFirst().orElse(null);
            if (prdDiscountResponse == null) {
                prdDiscountResponse = productDiscountHomeResponseDtoList.get(finalIndex).toResponse();
                productDiscountHomeResponses.add(prdDiscountResponse);
            } else {
                int i = productDiscountHomeResponses.indexOf(prdDiscountResponse);
                productDiscountHomeResponses.get(i).comparePrice(productDiscountHomeResponseDtoList.get(finalIndex).getPrice());
//                productDiscountHomeResponses.get(i).comparePriceDiscount(calculatePriceToPriceDiscount(productDiscountHomeResponseDtoList.get(finalIndex).getPrice(), productDiscountHomeResponseDtoList.get(finalIndex).getValue()));
            }
        }
        return productDiscountHomeResponses;
    }

    @Override
    public StringBuilder pageListColor(List<Integer> listColorId) {
        if (listColorId == null) {
            return new StringBuilder("");
        }
        StringBuilder prefix = new StringBuilder("");
        StringBuilder fullPage = new StringBuilder("");
        for (Integer integer : listColorId) {
            prefix = new StringBuilder("&listColorId=");
            prefix.append(integer);
            fullPage.append(prefix);
        }
        return fullPage;
    }

    @Override
    public StringBuilder pageListSize(List<Integer> listSizeId) {
        if (listSizeId == null) {
            return new StringBuilder("");
        }
        StringBuilder prefix = new StringBuilder("");
        StringBuilder fullPage = new StringBuilder("");
        for (Integer integer : listSizeId) {
            prefix = new StringBuilder("&listSizeId=");
            prefix.append(integer);
            fullPage.append(prefix);
        }
        return fullPage;
    }

    @Override
    public StringBuilder pageListCategory(List<Integer> listCategoryId) {
        if (listCategoryId == null) {
            return new StringBuilder("");
        }
        StringBuilder prefix = new StringBuilder("");
        StringBuilder fullPage = new StringBuilder("");
        for (Integer integer : listCategoryId) {
            prefix = new StringBuilder("&listCategoryId=");
            prefix.append(integer);
            fullPage.append(prefix);
        }
        return fullPage;
    }

    @Override
    public StringBuilder pageListForm(List<Integer> listFormId) {
        if (listFormId == null) {
            return new StringBuilder("");
        }
        StringBuilder prefix = new StringBuilder("");
        StringBuilder fullPage = new StringBuilder("");
        for (Integer integer : listFormId) {
            prefix = new StringBuilder("&listFormId=");
            prefix.append(integer);
            fullPage.append(prefix);
        }
        return fullPage;
    }

    @Override
    public StringBuilder pageListMaterial(List<Integer> listMaterialId) {
        if (listMaterialId == null) {
            return new StringBuilder("");
        }
        StringBuilder prefix = new StringBuilder("");
        StringBuilder fullPage = new StringBuilder("");
        for (Integer integer : listMaterialId) {
            prefix = new StringBuilder("&listMaterialId=");
            prefix.append(integer);
            fullPage.append(prefix);
        }
        return fullPage;
    }

    @Override
    public StringBuilder pageListBrand(List<Integer> listBrandId) {
        if (listBrandId == null) {
            return new StringBuilder("");
        }
        StringBuilder prefix = new StringBuilder("");
        StringBuilder fullPage = new StringBuilder("");
        for (Integer integer : listBrandId) {
            prefix = new StringBuilder("&listBrandId=");
            prefix.append(integer);
            fullPage.append(prefix);
        }
        return fullPage;
    }

    @Override
    public List<Integer> removeNullValueInList(List<Integer> list) {
        if (list == null || list.size() == 0) {
            return null;
        } else {
            list.removeIf(Objects::isNull);
            return list;
        }
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
