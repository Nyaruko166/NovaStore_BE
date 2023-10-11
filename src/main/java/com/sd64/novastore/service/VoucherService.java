package com.sd64.novastore.service;

import com.sd64.novastore.model.Role;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.request.RoleRequest;
import com.sd64.novastore.request.VoucherRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VoucherService {

    List<Voucher> getAll();

    Page<Voucher> getPage(int page);

    Voucher add(VoucherRequest voucherRequest);

    Voucher update(VoucherRequest voucherRequest, Integer id);

    Boolean delete(Integer id);

    Page<Voucher> search(String name, int page);
}
