package com.sd64.novastore.repository.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.dto.common.ProductDetailAndValueDiscountDto;
import com.sd64.novastore.dto.common.ProductDiscountHomeDto;
import com.sd64.novastore.dto.common.ProductHomeDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductViewRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p "+
            " FROM Product p " +
            " INNER JOIN Image i ON i.product.id = p.id " +
            " WHERE p.status = 1 ORDER BY p.updateDate DESC")
    List<Product> getAllProductResponse();

    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price " +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "WHERE p.status = 1 AND i.status = 1 AND pd.status = 1 OR p.status = 2\n  " +
            "ORDER BY p.updateDate DESC ")
    List<ProductHomeDto> getAllProductResponseHome();

    @Query(value = "SELECT top 10 p.id as productId,\n" +
            "            p.name as productName,\n" +
            "            pd.price as price, \n" +
            "            pd.priceDiscount as priceDiscount,\n" +
            "            pr.value as value\n" +
            "            FROM Product p\n" +
            "            INNER JOIN ProductDetail pd ON pd.productid = p.id\n" +
            "            INNER JOIN Image i ON i.productid = p.id\n" +
            "            LEFT JOIN PromotionDetail prd ON prd.productid = p.id\n" +
            "            LEFT JOIN Promotion pr ON pr.id = prd.promotionid\n" +
            "           WHERE p.status IN (1,2) AND i.status = 1 AND pd.status = 1\n" +
            "            ORDER BY NEWID() ", nativeQuery = true)
    List<ProductDiscountHomeDto> getAllProductAndDiscountResponseHome();


    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pd.priceDiscount as priceDiscount, " +
            "pr.value as value " +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "INNER JOIN PromotionDetail prd ON prd.product.id = p.id\n" +
            "INNER JOIN Promotion pr ON pr.id = prd.promotion.id\n" +
            "WHERE p.status = 2 AND i.status = 1 AND pd.status = 1\n  " +
            "ORDER BY p.updateDate DESC ")
    List<ProductDiscountHomeDto> getAllProductDiscountResponseHome();

    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pd.priceDiscount as priceDiscount, " +
            "pr.value as value " +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "LEFT JOIN PromotionDetail prd ON prd.product.id = p.id\n" +
            "LEFT JOIN Promotion pr ON pr.id = prd.promotion.id\n" +
            "WHERE p.status IN (1,2) AND i.status = 1 AND pd.status = 1\n  " +
            "ORDER BY p.updateDate DESC ")
    List<ProductDiscountHomeDto> getAllProductAndProductDiscountDiscountShopResponse();

    @Query(value = "SELECT pr.value as value, " +
            "pd.id as productDetailId " +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "LEFT JOIN PromotionDetail prd ON prd.product.id = p.id\n" +
            "LEFT JOIN Promotion pr ON pr.id = prd.promotion.id\n" +
            "WHERE p.status IN (1,2) AND i.status = 1 AND pd.status = 1 AND pd.id =:productDetailId \n  " +
            "GROUP BY pd.id, pr.value")
    ProductDetailAndValueDiscountDto getProductDetailDto(Integer productDetailId);


    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price " +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "WHERE p.status = 1 AND i.status = 1 AND pd.status = 1\n  " +
            "ORDER BY FUNCTION('RAND')")
    List<ProductHomeDto> getAllProductYouMayLike();

    @Query(value = "SELECT pd FROM ProductDetail pd\n" +
            "INNER JOIN Product p ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "WHERE p.status = 1 AND pd.status = 1 AND p.id =:productId AND pd.size.id =:sizeId AND pd.color.id =:colorId\n")
    ProductDetail getProductDetailByProductIdAndSizeIdAndColorId(Integer productId, Integer sizeId, Integer colorId);



//    @Query(value = "SELECT p.id as productId, " +
////            "p.code as code, " +
//            "p.name as productName, " +
////            "p.description as description, " +
//            "pd.price as price, " +
//            "p.brand.name as brandName, " +
//            "p.category.name as categoryName, " +
//            "p.form.name as formName, " +
//            "p.material.name as materialName " +
//            " FROM Product p " +
//            " INNER JOIN ProductDetail pd " +
//            " ON p.id = pd.product.id " +
//            " WHERE (p.name LIKE %:productNameSearch% OR p.name IS NULL) " +
////            " AND (p.description LIKE %:description% OR p.description IS NULL) " +
//            " AND (pd.price >= :priceMin AND pd.price <= :priceMax) " +
//            " AND (p.brand.id=:brandId OR :brandId IS NULL) " +
//            " AND (p.material.id=:materialId OR :materialId IS NULL) " +
//            " AND (p.category.id=:categoryId OR :categoryId IS NULL) " +
//            " AND (p.form.id=:formId OR :formId IS NULL) " +
//            " AND p.status = 1 AND pd.status = 1 ORDER BY p.updateDate DESC")
//    List<ProductHomeDto> searchProductResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productNameSearch, BigDecimal priceMax, BigDecimal priceMin);


    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pd.priceDiscount as priceDiscount, " +
            "pr.value as value " +
            " FROM Product p " +
            " INNER JOIN ProductDetail pd ON p.id = pd.product.id " +
            " LEFT JOIN PromotionDetail prd ON prd.product.id = p.id " +
            " LEFT JOIN Promotion pr ON pr.id = prd.promotion.id " +
            " INNER JOIN Image i ON i.product.id = p.id " +
            " WHERE (p.name LIKE %:productName% OR p.name IS NULL) " +
            " AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            " AND (p.brand.id=:brandId OR :brandId IS NULL) " +
            " AND (p.material.id=:materialId OR :materialId IS NULL) " +
            " AND (p.category.id=:categoryId OR :categoryId IS NULL) " +
            " AND (p.form.id=:formId OR :formId IS NULL) " +
            " AND p.status = 2 AND pd.status = 1 AND i.status = 1 ORDER BY p.updateDate DESC")
    List<ProductDiscountHomeDto> searchOnlyProductDiscountShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productName, BigDecimal priceMax, BigDecimal priceMin);


    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pd.priceDiscount as priceDiscount, " +
            "pr.value as value " +
            " FROM Product p " +
            " INNER JOIN ProductDetail pd ON p.id = pd.product.id " +
            " LEFT JOIN PromotionDetail prd ON prd.product.id = p.id " +
            " LEFT JOIN Promotion pr ON pr.id = prd.promotion.id " +
            " INNER JOIN Image i ON i.product.id = p.id " +
            " WHERE (p.name LIKE %:productName% OR p.name IS NULL) " +
            " AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            " AND (p.brand.id=:brandId OR :brandId IS NULL) " +
            " AND (p.material.id=:materialId OR :materialId IS NULL) " +
            " AND (p.category.id=:categoryId OR :categoryId IS NULL) " +
            " AND (p.form.id=:formId OR :formId IS NULL) " +
            " AND p.status IN (1,2) AND pd.status = 1 AND i.status = 1 ORDER BY p.updateDate DESC")
    List<ProductDiscountHomeDto> searchProductAndProductDiscountShopResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productName, BigDecimal priceMax, BigDecimal priceMin);


    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pr.value as value " +
            " FROM Product p " +
            " INNER JOIN ProductDetail pd ON p.id = pd.product.id " +
            " LEFT JOIN PromotionDetail prd ON prd.product.id = p.id " +
            " LEFT JOIN Promotion pr ON pr.id = prd.promotion.id " +
            " INNER JOIN Image i ON i.product.id = p.id " +
            " WHERE (p.name LIKE %:productName% OR p.name IS NULL) " +
            " AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            " AND (p.brand.id=:brandId OR :brandId IS NULL) " +
            " AND (p.material.id=:materialId OR :materialId IS NULL) " +
            " AND (p.category.id=:categoryId OR :categoryId IS NULL) " +
            " AND (p.form.id=:formId OR :formId IS NULL) " +
            " AND p.status IN (1, 2) AND pd.status = 1 AND i.status = 1 ORDER BY pd.price DESC")
    List<ProductDiscountHomeDto> searchProductAndProductDiscountDescResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productName, BigDecimal priceMax, BigDecimal priceMin);

    @Query(value = "SELECT p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pr.value as value " +
            " FROM Product p " +
            " INNER JOIN ProductDetail pd ON p.id = pd.product.id " +
            " LEFT JOIN PromotionDetail prd ON prd.product.id = p.id " +
            " LEFT JOIN Promotion pr ON pr.id = prd.promotion.id " +
            " INNER JOIN Image i ON i.product.id = p.id " +
            " WHERE (p.name LIKE %:productName% OR p.name IS NULL) " +
            " AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            " AND (p.brand.id=:brandId OR :brandId IS NULL) " +
            " AND (p.material.id=:materialId OR :materialId IS NULL) " +
            " AND (p.category.id=:categoryId OR :categoryId IS NULL) " +
            " AND (p.form.id=:formId OR :formId IS NULL) " +
            " AND p.status IN (1, 2) AND pd.status = 1 AND i.status = 1 ORDER BY pd.price DESC")
    List<ProductDiscountHomeDto> searchProductAndProductDiscountAscResponse(Integer brandId, Integer categoryId, Integer formId, Integer materialId, String productName, BigDecimal priceMax, BigDecimal priceMin);

}
