package com.sd64.novastore.repository;

import com.sd64.novastore.model.Image;
import com.sd64.novastore.model.ProductDetail;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findAllByStatus(Integer status);

    Image findByIdAndStatus(Integer id, Integer status);

    Page<Image> findAllByProductIdAndStatusOrderByUpdateDateDesc(Integer productId, Integer status, Pageable pageable);

    Page<Image> getAllImageByProduct_IdAndStatusOrderByUpdateDateDesc(Pageable pageable, Integer id, Integer status);

    Image findTopByProductIdAndStatusOrderByUpdateDateDesc(Integer productId, Integer status);

    List<Image> findAllByProduct_IdAndStatusOrderByUpdateDateDesc(Integer productId, Integer status);

    Image findTopByProduct_IdAndStatusOrderByUpdateDateDesc(Integer productId, Integer status);

}
