package com.sd64.novastore.service;

import com.sd64.novastore.request.BillDetailRequest;
import com.sd64.novastore.model.BillDetail;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillDetailService {
    List<BillDetail> getAll();

    Page<BillDetail> getAllPT(Integer page);

    BillDetail add(BillDetailRequest billDetailRequest);

    BillDetail update(BillDetailRequest billDetailRequest, Integer id);

    BillDetail delete(Integer id);
}
