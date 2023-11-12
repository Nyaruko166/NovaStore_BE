package com.sd64.novastore.repository.user;

import com.sd64.novastore.dto.user.ProductViewDto;
import com.sd64.novastore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductViewRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where p.status = 1")
    List<Product> getAllProductView();
}
