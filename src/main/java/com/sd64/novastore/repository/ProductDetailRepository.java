package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.ProductDetailDto;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.response.ProductHomeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    Page<ProductDetail> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<ProductDetail> getAllProductDetailByProduct_IdAndStatusOrderByUpdateDateDesc(Pageable pageable, Integer id, Integer status);

    List<ProductDetail> findAllByProductIdAndStatusOrderByUpdateDateDesc(Integer productId, Integer status);

    List<ProductDetail> findAllByProduct_IdOrderByIdAsc(Integer productId);

    ProductDetail findTopByOrderByIdDesc();

    ProductDetail findByIdAndStatus(Integer id, Integer status);

    @Query(value = "SELECT pd FROM ProductDetail pd\n" +
            "INNER JOIN Product p ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "WHERE p.status = 1 OR p.status = 2 AND pd.status = 1 AND p.id =:productId\n" +
            "ORDER BY pd.price DESC")
    List<ProductDetail> getAllProductDetailByProductIdOrderByPriceDesc(Integer productId);

    @Query(value = "SELECT pd FROM ProductDetail pd\n" +
            "INNER JOIN Product p ON pd.product.id = p.id\n" +
            "INNER JOIN Image i ON i.product.id = p.id\n" +
            "WHERE p.status = 1 OR p.status = 2 AND pd.status = 1 AND p.id =:productId\n" +
            "ORDER BY pd.price ASC")
    List<ProductDetail> getAllProductDetailByProductIdOrderByPriceAsc(Integer productId);

    @Query(value = "SELECT pd.id as id, " +
            " pd.code as code, " +
            " pd.quantity as quantity," +
            " pd.price as price," +
            " s.name as sizeName," +
            " c.name as colorName" +
            " FROM ProductDetail pd \n" +
            "INNER JOIN Size s ON s.id = pd.size.id \n " +
            "INNER JOIN Color c ON c.id = pd.color.id \n " +
            "INNER JOIN Product p ON p.id = pd.product.id \n" +
            "WHERE p.id =:productId \n" +
            "AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            "AND ( s.id =:sizeId OR :sizeId IS NULL )\n" +
            "AND ( c.id =:colorId OR :colorId IS NULL)\n" +
            "AND pd.status = 1\n " +
            "ORDER BY pd.updateDate DESC")
    Page<ProductDetailDto> getProductByPriceAndSizeIdAndColorId(Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId, Pageable pageable);


    @Query(value = "SELECT pd.id as id," +
            " pd.quantity as quantity," +
            " pd.price as price," +
            " s.name as sizeName," +
            " c.name as colorName" +
            " FROM ProductDetail pd \n" +
            "INNER JOIN Size s ON s.id = pd.size.id \n " +
            "INNER JOIN Color c ON c.id = pd.color.id \n " +
            "INNER JOIN Product p ON p.id = pd.product.id \n" +
            "WHERE p.id =:productId \n" +
            "AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            "AND ( s.id =:sizeId OR :sizeId IS NULL )\n" +
            "AND ( c.id =:colorId OR :colorId IS NULL)\n" +
            "AND pd.status = 1\n " +
            "ORDER BY pd.updateDate DESC")
    List<ProductDetailDto> getProductByPriceAndSizeIdAndColorId(Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId);


    @Query(value = "SELECT pd.id as id, " +
            " pd.code as code, " +
            " pd.quantity as quantity," +
            " pd.price as price," +
            " s.name as sizeName," +
            " c.name as colorName" +
            " FROM ProductDetail pd \n" +
            "INNER JOIN Size s ON s.id = pd.size.id \n " +
            "INNER JOIN Color c ON c.id = pd.color.id \n " +
            "INNER JOIN Product p ON p.id = pd.product.id \n" +
            "WHERE p.id =:productId \n" +
            "AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            "AND ( s.id =:sizeId OR :sizeId IS NULL )\n" +
            "AND ( c.id =:colorId OR :colorId IS NULL)\n" +
            "AND pd.status = 0\n " +
            "ORDER BY pd.updateDate DESC")
    Page<ProductDetailDto> getProductByPriceAndSizeIdAndColorIdDeleted(Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId, Pageable pageable);


    @Query(value = "SELECT pd.id as id," +
            " pd.quantity as quantity," +
            " pd.price as price," +
            " s.name as sizeName," +
            " c.name as colorName" +
            " FROM ProductDetail pd \n" +
            "INNER JOIN Size s ON s.id = pd.size.id \n " +
            "INNER JOIN Color c ON c.id = pd.color.id \n " +
            "INNER JOIN Product p ON p.id = pd.product.id \n" +
            "WHERE p.id =:productId \n" +
            "AND (pd.price >= :priceMin AND pd.price <= :priceMax)" +
            "AND ( s.id =:sizeId OR :sizeId IS NULL )\n" +
            "AND ( c.id =:colorId OR :colorId IS NULL)\n" +
            "AND pd.status = 0\n " +
            "ORDER BY pd.updateDate DESC")
    List<ProductDetailDto> getProductByPriceAndSizeIdAndColorIdDeleted(Integer productId, BigDecimal priceMin, BigDecimal priceMax, Integer sizeId, Integer colorId);
}
