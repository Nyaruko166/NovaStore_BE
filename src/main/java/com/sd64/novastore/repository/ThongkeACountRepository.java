package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.thongke.TKTong;
import com.sd64.novastore.dto.admin.thongke.TKTuan;
import com.sd64.novastore.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongkeACountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT COUNT(*) AS SoLuong\n" +
            "FROM Account\n" +
            "WHERE RoleId = 2", nativeQuery = true)
    public TKTong getTKKhachHang();
}
