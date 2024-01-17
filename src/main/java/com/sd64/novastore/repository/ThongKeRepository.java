package com.sd64.novastore.repository;

import com.sd64.novastore.dto.admin.thongke.SosanPhamBanDuoc;
import com.sd64.novastore.dto.admin.thongke.TKKhoangNgay;
import com.sd64.novastore.dto.admin.thongke.TKNam;
import com.sd64.novastore.dto.admin.thongke.TKNgay;
import com.sd64.novastore.dto.admin.thongke.TKSLThang;
import com.sd64.novastore.dto.admin.thongke.TKSoLuongSanPham;
import com.sd64.novastore.dto.admin.thongke.TKThang;
import com.sd64.novastore.dto.admin.thongke.TKTong;
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
    @Query(value = "SELECT\n" +
            "    COUNT( CASE WHEN b.Status = 1 AND b.CompletionDate IS NOT NULL THEN b.Id END) AS SoLuongThanhCong,\n" +
            "    SUM( CASE WHEN b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) = CONVERT(DATE, GETDATE()) THEN (b.Price - b.DiscountAmount) ELSE 0 END) AS DoanhThu,\n" +
            "    COUNT( CASE WHEN b.Status = 0 AND b.CancellationDate IS NOT NULL AND CONVERT(DATE, b.CancellationDate) = CONVERT(DATE, GETDATE()) THEN b.Id END) AS SoLuongHuy\n" +
            "FROM\n" +
            "    Bill b\n" +
            "WHERE\n" +
            "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND CONVERT(DATE, b.CompletionDate) = CONVERT(DATE, GETDATE()))\n" +
            "    OR\n" +
            "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) = CONVERT(DATE, GETDATE()))\n" +
            "    OR\n" +
            "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND CONVERT(DATE, b.CancellationDate) = CONVERT(DATE, GETDATE()))", nativeQuery = true)
    public TKNgay getThongKeNgay();


    @Query(value = "SELECT\n" +
            "\t\t\tSUM(CASE WHEN b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) = CONVERT(DATE, GETDATE()) THEN bd.Quantity ELSE 0 END) AS SoSanPham\n" +
            "\t\t\tFROM\n" +
            "                Bill b\n" +
            "\t\t\tLEFT JOIN\n" +
            "            BillDetail bd ON b.Id = bd.BillId\n" +
            "\t\t\twhere (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) = CONVERT(DATE, GETDATE()))", nativeQuery = true)
    public SosanPhamBanDuoc getThongKeSanPhamBanDuocNgay();

    @Query(value = "SET DATEFIRST 1;\r\n"
            + "\r\n"
            + "DECLARE @CurrentYear INT, @CurrentWeek INT;\r\n"
            + "SET @CurrentYear = YEAR(GETDATE());\r\n"
            + "SET @CurrentWeek = DATEPART(WEEK, GETDATE());\r\n"
            + "\r\n"
            + "SELECT\r\n"
            + "    COUNT( CASE WHEN b.Status = 1 AND b.CompletionDate IS NOT NULL THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM( CASE WHEN  b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = @CurrentYear AND DATEPART(WEEK,  b.PaymentDate) = @CurrentWeek THEN  (b.Price -b.DiscountAmount) ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT( CASE WHEN  b.Status = 0 AND b.CancellationDate IS NOT NULL AND YEAR(b.CancellationDate) = @CurrentYear AND DATEPART(WEEK,  b.CancellationDate) = @CurrentWeek THEN b.Id END) AS SoLuongHuy\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND YEAR(b.CompletionDate) = @CurrentYear AND DATEPART(WEEK, b.CompletionDate) = @CurrentWeek)\r\n"
            + "    OR\r\n" +
            "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = @CurrentYear AND DATEPART(WEEK, b.PaymentDate) = @CurrentWeek)\n" +
            "    OR\n" +
            "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND YEAR(b.CancellationDate) = @CurrentYear AND DATEPART(WEEK, b.CancellationDate) = @CurrentWeek);", nativeQuery = true)
    public TKTuan getThongKeTuan();

    @Query(value = "SET DATEFIRST 1;\r\n"
            + "\r\n"
            + "DECLARE @CurrentYear INT, @CurrentWeek INT;\r\n"
            + "SET @CurrentYear = YEAR(GETDATE());\r\n"
            + "SET @CurrentWeek = DATEPART(WEEK, GETDATE());\r\n"
            + "\r\n"
            + "SELECT\r\n"
            + "	   SUM(CASE WHEN b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = @CurrentYear AND DATEPART(WEEK,  b.PaymentDate) = @CurrentWeek THEN (bd.Quantity) END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n" +
            "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = @CurrentYear AND DATEPART(WEEK, b.PaymentDate) = @CurrentWeek)\n"
            , nativeQuery = true)
    public SosanPhamBanDuoc getThongKeSosanPhamBanDuocTuan();


    @Query(value = "SELECT\r\n"
            + "    COUNT( CASE WHEN b.Status = 1 AND b.CompletionDate IS NOT NULL THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM( CASE WHEN  b.Status <> 0 AND b.PaymentDate IS NOT NULL AND MONTH(b.PaymentDate) = MONTH(GETDATE()) THEN (b.Price -b.DiscountAmount) ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT( CASE WHEN b.Status = 0 AND b.CancellationDate IS NOT NULL THEN b.Id END) AS SoLuongHuy\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND MONTH(b.CompletionDate) = MONTH(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND MONTH(b.PaymentDate) = MONTH(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND MONTH(b.CancellationDate) = MONTH(GETDATE()));", nativeQuery = true)
    public TKThang getThongKeThang();

    @Query(value = "SELECT\r\n"
            + "    SUM(CASE WHEN b.Status <> 0 AND MONTH(b.PaymentDate) = MONTH(GETDATE()) THEN bd.Quantity ELSE 0 END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND MONTH(b.PaymentDate) = MONTH(GETDATE()))\r\n"
           , nativeQuery = true)
    public SosanPhamBanDuoc getThongKeSosanPhamBanDuocThang();


    @Query(value = "SELECT\r\n"
            + "    COUNT( CASE WHEN b.Status = 1 AND b.CompletionDate IS NOT NULL THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM( CASE WHEN b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = YEAR(GETDATE()) THEN (b.Price -b.DiscountAmount) ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT( CASE WHEN b.Status = 0 AND b.CancellationDate IS NOT NULL THEN b.Id END) AS SoLuongHuy\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND YEAR(b.CompletionDate) = YEAR(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = YEAR(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND YEAR(b.CancellationDate) = YEAR(GETDATE()));", nativeQuery = true)
    public TKNam getThongKeNam();

    @Query(value = "SELECT\r\n"
            + "    SUM( CASE WHEN b.Status <> 0 AND YEAR(b.PaymentDate) = YEAR(GETDATE()) THEN bd.Quantity ELSE 0 END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND YEAR(b.CompletionDate) = YEAR(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND YEAR(b.PaymentDate) = YEAR(GETDATE()))\r\n"
            + "    OR\r\n"
            + "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND YEAR(b.CancellationDate) = YEAR(GETDATE()));", nativeQuery = true)
    public SosanPhamBanDuoc getThongKeSosanPhamBanDuocNam();


    @Query(value = "Select SUM(bd.Quantity) as 'SoLuong' from BillDetail bd\n" +
            "join Bill b on b.Id = bd.BillId\n" +
            "WHERE b.Status = 1 AND MONTH(b.OrderDate) = MONTH(GETDATE()) AND YEAR(b.CompletionDate) = YEAR(GETDATE())", nativeQuery = true)
    public TKSLThang getThongKeSoLuongThang();

    @Query(value = "DECLARE @StartDate DATE = :tungay\r\n"
            + "DECLARE @EndDate DATE = :denngay\r\n"
            + "\r\n"
            + "SELECT\r\n"
            + "    COUNT( CASE WHEN b.Status = 1 AND b.CompletionDate IS NOT NULL AND CONVERT(DATE, b.CompletionDate) BETWEEN @StartDate AND @EndDate THEN b.Id END) AS SoLuongThanhCong,\r\n"
            + "    SUM( CASE WHEN b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) BETWEEN @StartDate AND @EndDate THEN (b.Price -b.DiscountAmount) ELSE 0 END) AS DoanhThu,\r\n"
            + "    COUNT( CASE WHEN b.Status = 0 AND b.CancellationDate IS NOT NULL AND CONVERT(DATE, b.CancellationDate) BETWEEN @StartDate AND @EndDate THEN b.Id END) AS SoLuongHuy\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND CONVERT(DATE, b.CompletionDate) BETWEEN @StartDate AND @EndDate) OR\r\n"
            + "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) BETWEEN @StartDate AND @EndDate) OR\r\n"
            + "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND CONVERT(DATE, b.CancellationDate) BETWEEN @StartDate AND @EndDate);", nativeQuery = true)
    public List<TKKhoangNgay> getTKKhoangNgay(@Param("tungay") String tungay, @Param("denngay") String denngay);

    @Query(value = "DECLARE @StartDate DATE = :tungay\r\n"
            + "DECLARE @EndDate DATE = :denngay\r\n"
            + "\r\n"
            + "SELECT\r\n"
            + "    SUM(CASE WHEN b.Status <> 0 AND CONVERT(DATE, b.PaymentDate) BETWEEN @StartDate AND @EndDate THEN bd.Quantity ELSE 0 END) AS SoSanPham\r\n"
            + "FROM\r\n"
            + "    Bill b\r\n"
            + "LEFT JOIN\r\n"
            + "    BillDetail bd ON b.Id = bd.BillId\r\n"
            + "WHERE\r\n"
            + "    (b.Status = 1 AND b.CompletionDate IS NOT NULL AND CONVERT(DATE, b.CompletionDate) BETWEEN @StartDate AND @EndDate) OR\r\n"
            + "    (b.Status <> 0 AND b.PaymentDate IS NOT NULL AND CONVERT(DATE, b.PaymentDate) BETWEEN @StartDate AND @EndDate) OR\r\n"
            + "    (b.Status = 0 AND b.CancellationDate IS NOT NULL AND CONVERT(DATE, b.CancellationDate) BETWEEN @StartDate AND @EndDate);", nativeQuery = true)
    public List<SosanPhamBanDuoc> getTKSosanPhamBanKhoangNgay(@Param("tungay") String tungay, @Param("denngay") String denngay);

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


    @Query(value = "SELECT\n" +
            "        SUM( CASE WHEN b.Status <> 0 AND b.PaymentDate IS NOT NULL THEN  (b.Price -b.DiscountAmount) ELSE 0 END) AS SoLuong\n" +
            "        FROM\n" +
            "           Bill b\n" +
            "        WHERE\n" +
            "           b.Status <> 0 AND b.PaymentDate IS NOT NULL", nativeQuery = true)
    public TKTong getTKTongDoanhThu();

    @Query(value = "SELECT COUNT(*) AS SoLuong\n" +
            "            FROM Bill b\n" +
            "            WHERE b.Status = 1 AND b.CompletionDate IS NOT NULL", nativeQuery = true)
    public TKTong getTKTongDonHang();

}
