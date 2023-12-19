package com.sd64.novastore.service.user;

import com.sd64.novastore.dto.common.ProductAndValueDiscountDto;
import com.sd64.novastore.dto.common.ProductDetailAndValueDiscountDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.response.ColorDetailResponse;
import com.sd64.novastore.response.ProductDiscountHomeResponse;
import com.sd64.novastore.response.ProductHomeResponse;
import com.sd64.novastore.response.SizeDetailResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductViewService {
    List<Product> getAllProductView();

    Product getOne(Integer id);

    Page<ProductHomeResponse> getAllProductHomeResponse(int page);

    List<ProductDiscountHomeResponse> getAllProductDiscountHomeResponse();

    List<ProductDiscountHomeResponse> getAllProductAndProductDiscountShopResponse();

    List<ProductDiscountHomeResponse> getAllProductAndProductDiscountHomeResponse();

    BigDecimal calculatePriceToPriceDiscount(BigDecimal price, Float value);

    List<ProductDiscountHomeResponse> setPriceDiscount(List<ProductDiscountHomeResponse> listProductDiscountHomeResponse);

    List<SizeDetailResponse> getAllSizeDetailResponse(Integer productId);
    List<ColorDetailResponse> getAllColorDetailResponse(Integer productId);

    BigDecimal getPriceMaxResponseByProductId(Integer productId);

    BigDecimal getPriceMinResponseByProductId(Integer productId);

    Page<ProductDiscountHomeResponse> convertlistToPage(List<ProductDiscountHomeResponse> listProductDiscount, int page);

    List<ProductDiscountHomeResponse> searchProductAndProductDiscountShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, int page);

    List<ProductDiscountHomeResponse> searchProductAndProductDiscountDescShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, int page);

    List<ProductDiscountHomeResponse> searchProductAscShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, int page);

    List<ProductDiscountHomeResponse> searchOnlyProductDiscountShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, int page);

    BigDecimal getPriceMaxBySelected(Integer price);

    BigDecimal getPriceMinBySelected(Integer price);

    BigDecimal getPriceDiscountMaxResponseByProductId(Integer productId);

    BigDecimal getPriceDiscountMinResponseByProductId(Integer productId);

    List<ProductDiscountHomeResponse> getRandomProductAndProductDiscount();

    ProductDetailAndValueDiscountDto getProductDetailAndValueDiscount(Integer productDetailId);

    Float getValueDiscountByProductId(Integer productId);

    ProductAndValueDiscountDto getProductAndValueDiscount(Integer productId);


}
