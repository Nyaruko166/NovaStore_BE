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

    Page<Image> findAllByStatus(Pageable pageable, Integer status);

    Page<Image> findAllByStatusOrderByUpdateDateDesc(Pageable pageable, Integer status);

    Page<Image> getAllImageByProduct_IdAndStatusOrderByUpdateDateDesc(Pageable pageable, Integer id, Integer status);

    Image findTopByProductIdOrderByUpdateDateDesc(Integer productId);

}
