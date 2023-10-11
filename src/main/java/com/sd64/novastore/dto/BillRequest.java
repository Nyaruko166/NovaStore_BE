package com.sd64.novastore.dto;

import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.Staff;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
@Getter
@Setter
public class BillRequest {

    @NotBlank(message = "customerName khong duoc de trong")
    private String customerName;

    @NotBlank(message = "address khong duoc de trong")
    private String address;

    @NotBlank(message = "phoneNumber khong duoc de trong")
    private String phoneNumber;

    @NotBlank(message = "note khong duoc de trong")
    private String note;


    private String orderDate;


    private String confirmationDate;


    private String shippingDate;


    private String receivedDate;


    private String completionDate;


    private String paymentDate;

    @NotNull(message = "shippingFee khong duoc de trong")
    private String shippingFee;


    private String totalPrice;


    private String createDate;


    private String updateDate;


    private String status;


    private String staffID;


    private String accountID;

    public Bill map(Bill bill) {
        bill.setCustomerName(this.getCustomerName());
        bill.setAddress(this.getAddress());
        bill.setPhoneNumber(this.getPhoneNumber());
        bill.setNote(this.getNote());
        bill.setOrderDate(Date.from(Instant.parse(this.getOrderDate())));
        bill.setConfirmationDate(Date.from(Instant.parse(this.getConfirmationDate())));
        bill.setShippingDate(Date.from(Instant.parse(this.getShippingDate())));
        bill.setReceivedDate(Date.from(Instant.parse(this.getReceivedDate())));
        bill.setCompletionDate(Date.from(Instant.parse(this.getCompletionDate())));
        bill.setPaymentDate(Date.from(Instant.parse(this.getPaymentDate())));
        bill.setShippingFee(BigDecimal.valueOf(Long.parseLong(this.getShippingFee())));
        bill.setTotalPrice(BigDecimal.valueOf(Long.parseLong(this.getTotalPrice())));
        bill.setCreateDate(Date.from(Instant.parse(this.getCreateDate())));
        bill.setUpdateDate(Date.from(Instant.parse(this.getUpdateDate())));
        bill.setStatus(Integer.valueOf(this.getStatus()));
        bill.setAccount(Account.builder().id(Integer.valueOf(this.getAccountID())).build());
        bill.setStaff(Staff.builder().id(Integer.valueOf(this.getStaffID())).build());
        return bill;
    }
}
