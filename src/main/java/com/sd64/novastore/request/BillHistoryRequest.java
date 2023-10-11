package com.sd64.novastore.request;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillHistory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class BillHistoryRequest {
    @NotBlank(message = "name khong duoc de trong")
    private String name;

    @NotBlank(message = "describe khong duoc de trong")
    private String describe;


    @NotNull(message = "Ngay createDate khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createDate;

    //    @PastOrPresent(message = "Ngay createDate nho hon hoac bang ngay hien tai")
    @NotNull(message = "Ngay createDate khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String updateDate;


    private String status;

    private String billId;

    private String accountID;

    public BillHistory map(BillHistory billHistory) {
        billHistory.setName(this.getName());
        billHistory.setDescribe(this.getDescribe());
        billHistory.setCreateDate(java.util.Date.from(Instant.parse(this.getCreateDate())));
        billHistory.setUpdateDate(Date.from(Instant.parse((this.getUpdateDate()))));
        billHistory.setStatus(Integer.valueOf(this.getStatus()));
        billHistory.setBill(Bill.builder().id(Integer.valueOf(this.getBillId())).build());
        billHistory.setAccount(Account.builder().id(Integer.valueOf(this.getAccountID())).build());
        return billHistory;
    }
}
