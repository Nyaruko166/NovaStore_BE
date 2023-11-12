package com.sd64.novastore.repository.user;

import com.sd64.novastore.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserColorRepository extends JpaRepository<Color, Integer> {
    @Query("SELECT DISTINCT c FROM Color c JOIN ProductDetail pd ON c.id = pd.color.id WHERE pd.status = 1 AND pd.product.id = :id")
    List<Color> getColorByProductId(@Param("id") Integer id);
}
