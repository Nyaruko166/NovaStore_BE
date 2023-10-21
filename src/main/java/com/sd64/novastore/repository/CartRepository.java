package com.sd64.novastore.repository;

import com.sd64.novastore.model.Brand;
import com.sd64.novastore.model.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Integer> {
//    List<Cart> findAllBy(Integer status);

//    Page<Cart> findAllByStatusOrderByUpdateDateDesc(Pageable pageable);
}
