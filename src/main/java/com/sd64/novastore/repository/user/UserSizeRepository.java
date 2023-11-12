package com.sd64.novastore.repository.user;

import com.sd64.novastore.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSizeRepository extends JpaRepository<Size, Integer> {
    @Query("SELECT DISTINCT s FROM Size s JOIN ProductDetail pd ON s.id = pd.size.id WHERE pd.status = 1 AND pd.product.id = :id")
    List<Size> getSizeByProductId(@Param("id") Integer id);
}
