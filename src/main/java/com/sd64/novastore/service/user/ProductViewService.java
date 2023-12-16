package com.sd64.novastore.service.user;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.response.ColorDetailResponse;
import com.sd64.novastore.response.ProductHomeResponse;
import com.sd64.novastore.response.SizeDetailResponse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface ProductViewService {
    List<Product> getAllProductView();

    Product getOne(Integer id);

    Page<ProductHomeResponse> getAllProductHomeResponse(int page);

    Page<ProductHomeResponse> getAllProductShopResponse(int page);

    List<ProductHomeResponse> getAllProductHomeResponse();

    List<ProductHomeResponse> getAllProductDiscountHomeResponse();

    List<SizeDetailResponse> getAllSizeDetailResponse(Integer productId);
    List<ColorDetailResponse> getAllColorDetailResponse(Integer productId);

    Page<ProductHomeResponse> getAllProductYouMayLike(int page);

    BigDecimal getPriceMaxResponseByProductId(Integer productId);

    BigDecimal getPriceMinResponseByProductId(Integer productId);

    BigDecimal getPriceProductDetailResponseByProductIdAndSizeIdAndColorId(Integer productId, Integer sizeId, Integer colorId);

    Page<ProductHomeResponse> searchProductShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, int page);

    BigDecimal getPriceMaxBySelected(Integer price);

    BigDecimal getPriceMinBySelected(Integer price);

}
