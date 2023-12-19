package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.thongke.TKKhoangNgay;
import com.sd64.novastore.dto.admin.thongke.TKNam;
import com.sd64.novastore.dto.admin.thongke.TKNgay;
import com.sd64.novastore.dto.admin.thongke.TKSLThang;
import com.sd64.novastore.dto.admin.thongke.TKSanPham;
import com.sd64.novastore.dto.admin.thongke.TKSoLuongSanPham;
import com.sd64.novastore.dto.admin.thongke.TKThang;
import com.sd64.novastore.dto.admin.thongke.TKTong;
import com.sd64.novastore.dto.admin.thongke.TKTrangThaiHoaDon;
import com.sd64.novastore.dto.admin.thongke.TKTuan;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ThongKeService {
    public TKNgay getTKNgay();

    public TKTuan getTKTuan();

    public TKThang getTKThang();

    public TKNam getTKNam();

    public TKSLThang getTKSLThang();

    public List<TKKhoangNgay> getTKSoLuongHD(String tungay, String denngay);

    public List<TKSoLuongSanPham> getTKSoLuongSanPham(String tungay, String denngay);

    public List<TKSanPham> getTKSanPham();

    public List<TKTrangThaiHoaDon> getTKTrangThaiHoaDon();

    public TKTong getTKKhachHang();
    public TKTong getTKTongSanPham();
    public TKTong getTKTongDonHang();

    public TKTong getTKTongDoanhThu();

    public TKTong getTKNhanVien();
}
