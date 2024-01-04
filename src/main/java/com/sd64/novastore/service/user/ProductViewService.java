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

    List<ProductDiscountHomeResponse> searchProductAndProductDiscountShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, Integer status);

    List<ProductDiscountHomeResponse> searchProductAndProductDiscountDescShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, Integer status);

    List<ProductDiscountHomeResponse> searchProductAndProductDiscountAscShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin, Integer status);

    List<ProductDiscountHomeResponse> searchOnlyProductDiscountShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin);

    BigDecimal getPriceMaxBySelected(Integer price);

    BigDecimal getPriceMinBySelected(Integer price);

    BigDecimal getPriceDiscountMaxResponseByProductId(Integer productId);

    BigDecimal getPriceDiscountMinResponseByProductId(Integer productId);

    List<ProductDiscountHomeResponse> getRandomProductAndProductDiscount();

    ProductDetailAndValueDiscountDto getProductDetailAndValueDiscount(Integer productDetailId);

    Float getValueDiscountByProductId(Integer productId);

    ProductAndValueDiscountDto getProductAndValueDiscount(Integer productId);

    StringBuilder pageListColor(List<Integer> listColorId);

    StringBuilder pageListSize(List<Integer> listSizeId);

    StringBuilder pageListBrand(List<Integer> listBrandId);

    StringBuilder pageListMaterial(List<Integer> listMaterialId);

    StringBuilder pageListCategory(List<Integer> lisCategoryId);

    StringBuilder pageListForm(List<Integer> listFormId);

    List<Integer> removeNullValueInList(List<Integer> list);

}
