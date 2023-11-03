package com.sd64.novastore.service;

import com.sd64.novastore.model.VoucherDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoucherDetailService {
    List<VoucherDetail> getAllVoucherDetail();

    Page<VoucherDetail> getAllPT(Integer page);

    VoucherDetail add(VoucherDetail voucherDetail);

    VoucherDetail update(VoucherDetail voucherDetail, Integer id);

    VoucherDetail delete(Integer id);

    VoucherDetail getOne(Integer id);
}
