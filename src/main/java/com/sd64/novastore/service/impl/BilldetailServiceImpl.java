package com.sd64.novastore.service.impl;

import com.sd64.novastore.request.BillDetailRequest;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.repository.BillDetailRepository;
import com.sd64.novastore.service.BillDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class BilldetailServiceImpl  implements BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    @Override
    public List<BillDetail> getAll() {
        return billDetailRepository.findAllByStatus(1);
    }

    @Override
    public Page<BillDetail> getAllPT(Integer page) {
        Pageable pageable= PageRequest.of(page,5);
        return billDetailRepository.findAllByStatus(pageable,1);
    }

    @Override
    public BillDetail add(BillDetailRequest billDetailRequest) {
        BillDetail billDetail= billDetailRequest.map(new BillDetail());
        return billDetailRepository.save(billDetail);
    }

    @Override
    public BillDetail update(BillDetailRequest billDetailRequest, Integer id) {
        Optional<BillDetail> billDetailOptional= billDetailRepository.findById(id);
        return billDetailOptional.map(billDetail -> {
            billDetail.setQuantity(Integer.valueOf(billDetailRequest.getQuantity()));
            billDetail.setPrice(BigDecimal.valueOf(Long.parseLong(billDetailRequest.getPrice())));
            billDetail.setStatus(Integer.valueOf(billDetailRequest.getStatus()));
            billDetail.setBill(Bill.builder().id(Integer.valueOf(billDetailRequest.getBillId())).build());
            billDetail.setProductDetail(ProductDetail.builder().id(Integer.valueOf(billDetailRequest.getProductDetailId())).build());
            return billDetailRepository.save(billDetail);
        }).orElse(null);
    }

    @Override
    public BillDetail delete(Integer id) {
        Optional<BillDetail> billDetailOptional= billDetailRepository.findById(id);
        return billDetailOptional.map(billDetail -> {
            billDetail.setStatus(0);
            billDetailRepository.save(billDetail);
            return billDetail;
        }).orElse(null);
    }
}
