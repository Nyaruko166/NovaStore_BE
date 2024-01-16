package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.thongke.TKTong;
import com.sd64.novastore.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongkeACountRepository extends JpaRepository<Account, Integer> {
    @Query(value = "SELECT COUNT(*) AS SoLuong\n" +
            "            FROM Account AS A\n" +
            "            JOIN Role AS R ON A.RoleId = R.Id\n" +
            "            WHERE  R.Status = 1 AND R.Name='Employee'",nativeQuery = true)
    public TKTong getTKNhanVien();

    @Query(value = "SELECT COUNT(*) AS SoLuong\n" +
            "            FROM Account AS A\n" +
            "            JOIN Role AS R ON A.RoleId = R.Id\n" +
            "            WHERE  R.Status = 1 AND R.Name='User'",nativeQuery = true)
    public TKTong getTKKhachHang();
}
