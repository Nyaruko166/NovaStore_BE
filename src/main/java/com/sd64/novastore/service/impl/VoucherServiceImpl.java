package com.sd64.novastore.service.impl;

import com.sd64.novastore.dto.admin.thongke.VoucherSearchDTO;
import com.sd64.novastore.model.Color;
import com.sd64.novastore.model.Voucher;
import com.sd64.novastore.repository.VoucherRepository;
import com.sd64.novastore.service.VoucherService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    private VoucherRepository voucherRepository;

    @Override
    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleUpdateExpiredVouchers() {
        List<Voucher> expiredVouchers = getExpiredVouchers();
        for (Voucher voucher : expiredVouchers) {
            updateExpiredVoucher(voucher);
        }
    }

    @Override
    public List<Voucher> getExpiredVouchers() {
        Date currentDate = new Date();
        return voucherRepository.findByEndDateBeforeAndStatus(currentDate, 1);
    }

    @Override
    public void updateExpiredVoucher(Voucher voucher) {
        voucher.setStatus(0);
        try {
            voucherRepository.save(voucher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Page<VoucherSearchDTO> searchVoucher(String code, Date ngayTaoStart, Date ngayTaoEnd, Integer status, String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.searchVoucher(code,ngayTaoStart,ngayTaoEnd,status,name,pageable);
    }

    @Override
    public List<Voucher> getAll() {
        return voucherRepository.findAllByStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Voucher> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAllByStatusOrderByIdDesc(pageable);
    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void updatePromotionStatus() {
        List<Voucher> vouchersToUpdate = voucherRepository.findAllByStatusOrderByIdDesc(2);
        Date currentDate = new Date();

        for (Voucher voucher : vouchersToUpdate) {
            if (voucher.getStartDate().before(currentDate)) {
                voucher.setStatus(1);
                voucherRepository.save(voucher);
            }
        }
    }
    public String generateCode() {
        Voucher voucher = voucherRepository.findTopByOrderByIdDesc();
        if (voucher == null) {
            return "M00001";
        }
        Integer idFinalPresent = voucher.getId() + 1;
        String code = String.format("%05d", idFinalPresent);
        return "M"+code;
    }
    @Override
    public Voucher add(Voucher voucher) {
        voucher.setCreateDate(new Date());
        voucher.setUpdateDate(new Date());
        voucher.updateStatus();
        voucher.setCode(generateCode());
        return voucherRepository.save(voucher);
    }

    @Override
    public Voucher update(Voucher voucher, Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        if (optional.isPresent()) {
            Voucher oldVoucher = optional.get();
            voucher.setId(oldVoucher.getId());
            voucher.setCreateDate(oldVoucher.getCreateDate());
            voucher.setUpdateDate(new Date());
            voucher.setStatus(oldVoucher.getStatus());
            voucher.updateStatus();
            return voucherRepository.save(voucher);
        } else {
            return null;
        }
    }

    @Override
    public Voucher delete(Integer id) {
        Optional<Voucher> optional = voucherRepository.findById(id);
        if (optional.isPresent()) {
            Voucher voucher = optional.get();
            voucher.setStatus(0);
            return voucherRepository.save(voucher);
        } else {
            return null;
        }
    }

    @Override
    public Voucher getOne(Integer id) {
        return voucherRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Voucher> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return voucherRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }

    @Override
    public List<Voucher> getVoucherByCartPrice(BigDecimal cartPrice) {
        return voucherRepository.getVoucherByCartPrice(cartPrice);
    }

    @Override
    public Voucher getVoucherById(Integer id) {
        return voucherRepository.findById(id).orElse(null);
    }


}
