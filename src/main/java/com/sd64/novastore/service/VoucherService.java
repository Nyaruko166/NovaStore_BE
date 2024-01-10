package com.sd64.novastore.service;

import com.sd64.novastore.dto.admin.thongke.PromotionSearchDTO;
import com.sd64.novastore.dto.admin.thongke.VoucherSearchDTO;
import com.sd64.novastore.model.Voucher;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface VoucherService {

    List<Voucher> getAll();

    Page<Voucher> getAll(Integer page);

    Boolean add(Voucher voucher);

    Boolean update(Voucher voucher, Integer id);

    Voucher delete(Integer id);

    Voucher getOne(Integer id);

    Page<Voucher> search(String name, int page);

    List<Voucher> getVoucherByCartPrice(BigDecimal cartPrice);

    Voucher getVoucherById(Integer id);

    public void scheduleUpdateExpiredVouchers();

    public void updatePromotionStatus();

    public List<Voucher> getExpiredVouchers();

    public void updateExpiredVoucher(Voucher voucher);

    Page<VoucherSearchDTO> searchVoucher(
            String code,
            Date ngayTaoStart,
            Date ngayTaoEnd,
            Integer status,
            String name,
            int page);


}
