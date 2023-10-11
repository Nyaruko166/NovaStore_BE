package com.sd64.novastore.repository;

import com.sd64.novastore.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository  extends JpaRepository<Cart,Integer> {

}
