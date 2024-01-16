package com.sd64.novastore.repository.user;

import com.sd64.novastore.dto.common.ProductAndValueDiscountDto;
import com.sd64.novastore.dto.common.ProductDiscountHomeDto;
import com.sd64.novastore.dto.common.ProductHomeDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.Size;
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

    @Query(value = "SELECT p.id as productId,\n" +
            "       p.name as productName,\n" +
            "       pd.price as price,\n" +
            "       pd.priceDiscount as priceDiscount,\n" +
            "       CASE WHEN prd.status = 0 THEN NULL ELSE pr.value END as value\n" +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.productid = p.id\n" +
            "INNER JOIN Image i ON i.productid = p.id\n" +
            "LEFT JOIN PromotionDetail prd ON prd.productid = p.id\n" +
            "LEFT JOIN Promotion pr ON pr.id = prd.promotionid\n" +
            "WHERE p.status IN (1,2) AND i.status = 1 AND pd.status = 1\n" +
            "ORDER BY p.updateDate DESC ", nativeQuery = true)
    List<ProductDiscountHomeDto> getAllProductAndDiscountResponseHome();

    @Query(value = "SELECT CASE WHEN prd.status = 0 THEN NULL ELSE pr.value END as value FROM Product p\n" +
            "            LEFT JOIN PromotionDetail prd ON prd.ProductId = p.id\n" +
            "            LEFT JOIN Promotion pr ON pr.Id = prd.PromotionId\n" +
            "            INNER JOIN Image i ON i.ProductId = p.Id\n" +
            "            INNER JOIN ProductDetail pd ON pd.ProductId = p.id\n" +
            "            WHERE p.status IN (1,2) AND i.Status = 1 AND p.id =:productId\n" +
            "            GROUP BY pr.value, prd.status", nativeQuery = true)
    Float getValueDiscountByProductId(Integer productId);

    @Query(value = "SELECT TOP 10 p.id as productId, " +
            "p.name as productName, " +
            "pd.price as price, " +
            "pd.priceDiscount as priceDiscount, " +
            "pr.value as value " +
            "FROM Product p\n" +
            "INNER JOIN ProductDetail pd ON pd.productId = p.id\n" +
            "INNER JOIN Image i ON i.productId = p.id\n" +
            "LEFT JOIN PromotionDetail prd ON prd.productId = p.id\n" +
            "LEFT JOIN Promotion pr ON pr.id = prd.promotionId\n" +
            "WHERE p.status IN (1,2) AND i.status = 1 AND pd.status = 1\n  " +
            "ORDER BY NEWID() ", nativeQuery = true)
    List<ProductDiscountHomeDto> getAllProductAndDiscountResponseRandom();


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


        @Query(value = "SELECT CASE WHEN prd.status = 0 THEN NULL ELSE pr.value END as value,\n" +
                "       p.id as productId\n" +
                "FROM Product p\n" +
                "INNER JOIN ProductDetail pd ON pd.productid = p.id\n" +
                "INNER JOIN Image i ON i.productid = p.id\n" +
                "LEFT JOIN PromotionDetail prd ON prd.productid = p.id\n" +
                "LEFT JOIN Promotion pr ON pr.id = prd.promotionid\n" +
                "WHERE p.status IN (1,2) AND i.status = 1 AND pd.status = 1 AND p.id =:productId\n" +
                "GROUP BY p.id, pr.value, prd.status", nativeQuery = true)
    ProductAndValueDiscountDto getProductDetailDto(Integer productId);


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
            " AND (pd.priceDiscount >= :priceMin AND pd.priceDiscount <= :priceMax)" +
            " AND (p.brand.id IN :listBrandId OR :listBrandId IS NULL) " +
            " AND (p.material.id IN :listMaterialId OR :listMaterialId IS NULL) " +
            " AND (p.category.id IN :listCategoryId OR :listCategoryId IS NULL) " +
            " AND (p.form.id IN :listFormId OR :listFormId IS NULL) " +
            " AND (pd.size.id IN :listSizeId OR :listSizeId IS NULL) " +
            " AND (pd.color.id IN :listColorId OR :listColorId IS NULL) " +
            " AND p.status = 2 AND pd.status = 1 AND i.status = 1 ORDER BY p.updateDate DESC")
    List<ProductDiscountHomeDto> searchOnlyProductDiscountShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productName, BigDecimal priceMax, BigDecimal priceMin);



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
            " AND (pd.priceDiscount >= :priceMin AND pd.priceDiscount <= :priceMax)" +
            " AND (p.brand.id IN :listBrandId OR :listBrandId IS NULL) " +
            " AND (p.material.id IN :listMaterialId OR :listMaterialId IS NULL) " +
            " AND (p.category.id IN :listCategoryId OR :listCategoryId IS NULL) " +
            " AND (p.form.id IN :listFormId OR :listFormId IS NULL) " +
            " AND (pd.size.id IN :listSizeId OR :listSizeId IS NULL) " +
            " AND (pd.color.id IN :listColorId OR :listColorId IS NULL) " +
            " AND p.status IN (:listStatus) AND pd.status = 1 AND i.status = 1 ORDER BY p.updateDate DESC")
    List<ProductDiscountHomeDto> searchProductAndProductDiscountShopResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productName, BigDecimal priceMax, BigDecimal priceMin, List<Integer> listStatus);



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
            " AND (pd.priceDiscount >= :priceMin AND pd.priceDiscount <= :priceMax)" +
            " AND (p.brand.id IN :listBrandId OR :listBrandId IS NULL) " +
            " AND (p.material.id IN :listMaterialId OR :listMaterialId IS NULL) " +
            " AND (p.category.id IN :listCategoryId OR :listCategoryId IS NULL) " +
            " AND (p.form.id IN :listFormId OR :listFormId IS NULL) " +
            " AND (pd.size.id IN :listSizeId OR :listSizeId IS NULL) " +
            " AND (pd.color.id IN :listColorId OR :listColorId IS NULL) " +
            " AND (p.status IN :listStatus OR :listStatus IS NULL) " +
            " AND pd.status = 1 AND i.status = 1 ORDER BY pd.priceDiscount DESC")
    List<ProductDiscountHomeDto> searchProductAndProductDiscountDescResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productName, BigDecimal priceMax, BigDecimal priceMin, List<Integer> listStatus);

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
            " AND (pd.priceDiscount >= :priceMin AND pd.priceDiscount <= :priceMax)" +
            " AND (p.brand.id IN :listBrandId OR :listBrandId IS NULL) " +
            " AND (p.material.id IN :listMaterialId OR :listMaterialId IS NULL) " +
            " AND (p.category.id IN :listCategoryId OR :listCategoryId IS NULL) " +
            " AND (p.form.id IN :listFormId OR :listFormId IS NULL) " +
            " AND (pd.size.id IN :listSizeId OR :listSizeId IS NULL) " +
            " AND (pd.color.id IN :listColorId OR :listColorId IS NULL) " +
            " AND p.status IN (:status) AND pd.status = 1 AND i.status = 1 ORDER BY pd.priceDiscount ASC")
    List<ProductDiscountHomeDto> searchProductAndProductDiscountAscResponse(List<Integer> listBrandId, List<Integer> listCategoryId, List<Integer> listFormId, List<Integer> listMaterialId, List<Integer> listSizeId, List<Integer> listColorId, String productName, BigDecimal priceMax, BigDecimal priceMin, Integer status);

}
