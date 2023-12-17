package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.thongke.TKKhoangNgay;
import com.sd64.novastore.dto.admin.thongke.TKNam;
import com.sd64.novastore.dto.admin.thongke.TKNgay;
import com.sd64.novastore.dto.admin.thongke.TKSLThang;
import com.sd64.novastore.dto.admin.thongke.TKSoLuongSanPham;
import com.sd64.novastore.dto.admin.thongke.TKThang;
import com.sd64.novastore.dto.admin.thongke.TKTrangThaiHoaDon;
import com.sd64.novastore.dto.admin.thongke.TKTuan;
import com.sd64.novastore.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongKeRepository extends JpaRepository<Bill, Integer> {
    @Query(value = "SELECT\r\n"
            + "    COUNT(CASE WHEN b.Status = 1 THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM(CASE WHEN b.Status = 1 AND CONVERT(DATE, b.OrderDate) = CONVERT(DATE, GETDATE()) THEN b.TotalPrice ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT(CASE WHEN b.Status = 0 THEN b.Id END) AS SoLuongHuy,\r\n"
            + "	SUM(CASE WHEN b.Status = 1 THEN (bd.Quantity) END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND CONVERT(DATE, b.OrderDate) = CONVERT(DATE, GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status = 0 AND CONVERT(DATE, b.OrderDate) = CONVERT(DATE, GETDATE()));", nativeQuery = true)
    public TKNgay getThongKeNgay();

    @Query(value = "SET DATEFIRST 1;\r\n"
            + "\r\n"
            + "DECLARE @CurrentYear INT, @CurrentWeek INT;\r\n"
            + "SET @CurrentYear = YEAR(GETDATE());\r\n"
            + "SET @CurrentWeek = DATEPART(WEEK, GETDATE());\r\n"
            + "\r\n"
            + "SELECT\r\n"
            + "    COUNT(CASE WHEN b.Status = 1 THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM(CASE WHEN  b.Status = 1 AND YEAR( b.OrderDate) = @CurrentYear AND DATEPART(WEEK,  b.OrderDate) = @CurrentWeek THEN  b.TotalPrice ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT(CASE WHEN  b.Status = 0 THEN b.Id END) AS SoLuongHuy,\r\n"
            + "	SUM(CASE WHEN b.Status = 1 THEN (bd.Quantity) END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    ( b.Status = 1 AND YEAR( b.OrderDate) = @CurrentYear AND DATEPART(WEEK,  b.OrderDate) = @CurrentWeek)\r\n"
            + "    OR\r\n"
            + "    ( b.Status = 0 AND YEAR( b.OrderDate) = @CurrentYear AND DATEPART(WEEK,  b.OrderDate) = @CurrentWeek);", nativeQuery = true)
    public TKTuan getThongKeTuan();

    @Query(value = "SELECT\r\n"
            + "    COUNT(CASE WHEN  b.Status = 1 THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM(CASE WHEN  b.Status = 1 AND MONTH( b.OrderDate) = MONTH(GETDATE()) THEN  b.TotalPrice ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT(CASE WHEN  b.Status = 0 THEN b.Id END) AS SoLuongHuy,\r\n"
            + "	SUM(CASE WHEN b.Status = 1 THEN (bd.Quantity) END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    ( b.Status = 1 AND MONTH( b.OrderDate) = MONTH(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    ( b.Status = 0 AND MONTH( b.OrderDate) = MONTH(GETDATE()));", nativeQuery = true)
    public TKThang getThongKeThang();

    @Query(value = "SELECT\r\n"
            + "    COUNT(CASE WHEN  b.Status = 1 THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM(CASE WHEN  b.Status = 1 AND YEAR(OrderDate) = YEAR(GETDATE()) THEN  b.TotalPrice ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT(CASE WHEN  b.Status = 0 THEN b.Id END) AS SoLuongHuy,\r\n"
            + "	SUM(CASE WHEN b.Status = 1 THEN (bd.Quantity) END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    ( b.Status = 1 AND YEAR( b.OrderDate) = YEAR(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    ( b.Status = 0 AND YEAR( b.OrderDate) = YEAR(GETDATE()));", nativeQuery = true)
    public TKNam getThongKeNam();

    @Query(value = "Select SUM(bd.Quantity) as 'SoLuong' from BillDetail bd\n" +
            "join Bill b on b.Id = bd.BillId\n" +
            "WHERE b.Status = 1 AND MONTH(b.OrderDate) = MONTH(GETDATE()) AND YEAR(b.OrderDate) = YEAR(GETDATE())", nativeQuery = true)
    public TKSLThang getThongKeSoLuongThang();

    @Query(value = "DECLARE @StartDate DATE = :tungay\r\n"
            + "DECLARE @EndDate DATE = :denngay\r\n"
            + "\r\n"
            + "SELECT\r\n"
            + "    COUNT(CASE WHEN b.Status = 1 THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM(CASE WHEN b.Status = 1 AND CONVERT(DATE, b.OrderDate) BETWEEN @StartDate AND @EndDate THEN b.TotalPrice ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT(CASE WHEN b.Status = 0 AND CONVERT(DATE, b.OrderDate) BETWEEN @StartDate AND @EndDate THEN b.Id END) AS SoLuongHuy,\r\n"
            + "    SUM(CASE WHEN b.Status = 1 AND CONVERT(DATE, b.OrderDate) BETWEEN @StartDate AND @EndDate THEN bd.Quantity ELSE 0 END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND CONVERT(DATE, b.OrderDate) BETWEEN @StartDate AND @EndDate) OR\r\n"
            + "    (b.Status = 0 AND CONVERT(DATE, b.OrderDate) BETWEEN @StartDate AND @EndDate);", nativeQuery = true)
    public List<TKKhoangNgay> getTKKhoangNgay(@Param("tungay") String tungay, @Param("denngay") String denngay);

    @Query(value = "WITH DateTable AS (\n" +
            "    SELECT \n" +
            "        DATEADD(DAY, number, :tungay) AS DateInInterval\n" +
            "    FROM master.dbo.spt_values\n" +
            "    WHERE type = 'P'\n" +
            "        AND DATEADD(DAY, number, :tungay) <= :denngay\n" +
            ")\n" +
            "\n" +
            "SELECT \n" +
            "    CAST(DateTable.DateInInterval AS DATE) AS PurchaseDay,\n" +
            "    COALESCE(SUM(bd.Quantity), 0) AS SoLuong,\n" +
            "    COALESCE(SUM(bd.Quantity * bd.Price), 0) AS DoanhThu\n" +
            "FROM DateTable\n" +
            "LEFT JOIN Bill b ON CONVERT(DATE, b.OrderDate) = DateTable.DateInInterval AND b.Status = 1\n" +
            "LEFT JOIN BillDetail bd ON bd.BillId = b.Id\n" +
            "GROUP BY CAST(DateTable.DateInInterval AS DATE)\n" +
            "ORDER BY OrderDate", nativeQuery = true)
    public List<TKSoLuongSanPham> getTKSoLuongSanPham(@Param("tungay") String tungay, @Param("denngay") String denngay);

    @Query(value = "SELECT b.Status, COUNT(*) AS TotalCount, " +
            "(COUNT(*) * 100.0 / SUM(COUNT(*)) OVER ()) AS Percentage " +
            "FROM Bill b " +
            "GROUP BY b.Status " +
            "ORDER BY b.Status", nativeQuery = true)
    public List<TKTrangThaiHoaDon> getTKTrangThaiHoaDon();

}
