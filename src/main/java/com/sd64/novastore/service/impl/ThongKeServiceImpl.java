package com.sd64.novastore.service.impl;


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
import com.sd64.novastore.repository.ThongKeRepository;
import com.sd64.novastore.repository.ThongKeTongSanPham;
import com.sd64.novastore.repository.ThongkeACountRepository;
import com.sd64.novastore.repository.ThongkeSanPhamRepository;
import com.sd64.novastore.service.ThongKeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongKeServiceImpl implements ThongKeService {
    @Autowired
    private ThongKeRepository thongKeRepository;
    @Autowired
    private ThongkeSanPhamRepository thongkeSanPhamRepository;
    @Autowired
    private ThongkeACountRepository thongkeACountRepository;

    @Autowired
    private ThongKeTongSanPham thongKeTongSanPham;

    public TKNgay getTKNgay() {
        return thongKeRepository.getThongKeNgay();
    }

    public TKTuan getTKTuan() {
        return thongKeRepository.getThongKeTuan();
    }

    public TKThang getTKThang() {
        return thongKeRepository.getThongKeThang();
    }

    public TKNam getTKNam() {
        return thongKeRepository.getThongKeNam();
    }

    public TKSLThang getTKSLThang() {
        return thongKeRepository.getThongKeSoLuongThang();
    }

    public List<TKKhoangNgay> getTKSoLuongHD(String tungay, String denngay) {
        return thongKeRepository.getTKKhoangNgay(tungay, denngay);
    }

    public List<TKSoLuongSanPham> getTKSoLuongSanPham(String tungay, String denngay) {
        return thongKeRepository.getTKSoLuongSanPham(tungay, denngay);
    }

    @Override
    public List<TKSanPham> getTKSanPham() {
        return thongkeSanPhamRepository.getTKSanPham();
    }

    @Override
    public List<TKTrangThaiHoaDon> getTKTrangThaiHoaDon() {
        return thongKeRepository.getTKTrangThaiHoaDon();
    }

    @Override
    public TKTong getTKKhachHang() {
        return thongkeACountRepository.getTKKhachHang();
    }

    @Override
    public TKTong getTKTongSanPham() {
        return thongKeTongSanPham.getTKTongSanPham();
    }

    @Override
    public TKTong getTKTongDonHang() {
        return thongKeRepository.getTKTongDonHang();
    }

    @Override
    public TKTong getTKTongDoanhThu() {
        return thongKeRepository.getTKTongDoanhThu();
    }


}
