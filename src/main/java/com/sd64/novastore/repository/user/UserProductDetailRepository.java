package com.sd64.novastore.repository.user;

import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    @Query("SELECT p FROM ProductDetail p WHERE p.status = 1 AND p.product.id = :id")
    List<ProductDetail> getAllProductDetail(@Param("id") Integer id);

    @Query(value = "SELECT Id FROM ProductDetail WHERE Status = 1 AND ProductId = :productId AND SizeId = :sizeId AND ColorId = :colorId", nativeQuery = true)
    Integer getProductDetailId(@Param("productId") Integer productId, @Param("sizeId") Integer sizeId, @Param("colorId") Integer colorId);

    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.id = :productId AND pd.size.id = :sizeId AND pd.color.id = :colorId")
    ProductDetail getProductDetail(@Param("productId") Integer productId, @Param("sizeId") Integer sizeId, @Param("colorId") Integer colorId);
}
