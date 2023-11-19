package com.sd64.novastore.repository;

import com.sd64.novastore.dto.ProductDetailDto;
import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<ProductDetail> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);

    Page<ProductDetail> getAllProductDetailByProduct_IdAndStatusOrderByUpdateDateDesc(Pageable pageable, Integer id, Integer status);

    Page<ProductDetail> searchAllByProduct_Id(Pageable pageable, Integer id);

    Optional<ProductDetail> findAllByCode(String code);

    @Query(value = "SELECT pd.id as id, " +
            " pd.code as code, " +
            " pd.quantity as quantity," +
            " s.name as sizeName," +
            " c.name as colorName" +
            " FROM ProductDetail pd \n" +
            "INNER JOIN Size s ON s.id = pd.size.id \n " +
            "INNER JOIN Color c ON c.id = pd.color.id \n " +
            "INNER JOIN Product p ON p.id = pd.product.id \n" +
            "WHERE p.id =:productId \n" +
            "AND ( s.id =:sizeId OR :sizeId IS NULL )\n" +
            "AND ( c.id =:colorId OR :colorId IS NULL)\n" +
            "AND pd.status = 1\n " +
            "ORDER BY pd.updateDate DESC")
    Page<ProductDetailDto> getProductBySizeIdAndColorId(Integer productId, Integer sizeId, Integer colorId, Pageable pageable);


    @Query(value = "SELECT pd.id as id," +
            " pd.quantity as quantity," +
            " s.name as sizeName," +
            " c.name as colorName" +
            " FROM ProductDetail pd \n" +
            "INNER JOIN Size s ON s.id = pd.size.id \n " +
            "INNER JOIN Color c ON c.id = pd.color.id \n " +
            "INNER JOIN Product p ON p.id = pd.product.id \n" +
            "WHERE p.id =:productId \n" +
            "AND ( s.id =:sizeId OR :sizeId IS NULL )\n" +
            "AND ( c.id =:colorId OR :colorId IS NULL)\n" +
            "AND pd.status = 1\n " +
            "ORDER BY pd.updateDate DESC")
    List<ProductDetailDto> getProductBySizeIdAndColorId(Integer productId, Integer sizeId, Integer colorId);
}
