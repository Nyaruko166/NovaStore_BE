package com.sd64.novastore.repository.user;

import com.sd64.novastore.dto.admin.ProductDto;
import com.sd64.novastore.dto.common.ProductResponseHomeDto;
import com.sd64.novastore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductViewRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.status = 1")
    List<Product> getAllProductView();


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
            "WHERE p.status = 1\n" +
            "ORDER BY p.updateDate DESC ")
    List<ProductResponseHomeDto> getAllProductResponseHome();
}
