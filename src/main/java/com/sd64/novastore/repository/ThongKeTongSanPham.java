package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.thongke.TKTong;
import com.sd64.novastore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongKeTongSanPham extends JpaRepository<Product,Integer> {
    @Query(value = "SELECT COUNT(*) AS SoLuong\n" +
            "FROM Product\n" +
            "WHERE Status IN (1, 2)", nativeQuery = true)
    public TKTong getTKTongSanPham();
}
