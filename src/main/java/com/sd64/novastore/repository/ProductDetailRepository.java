package com.sd64.novastore.repository;

import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    List<ProductDetail> findAllByAndStatusOrderByIdDesc(Integer status);

    Page<ProductDetail> findAllByAndStatusOrderByIdDesc(Pageable pageable, Integer status);


    //    @Query(value = "SELECT pd" +
//            " FROM ProductDetail pd")
    Page<ProductDetail> getAllProductDetailByProduct_IdAndStatusOrderByUpdateDateDesc(Pageable pageable, Integer id, Integer status);

    Page<ProductDetail> searchAllByProduct_Id(Pageable pageable, Integer id);
}
