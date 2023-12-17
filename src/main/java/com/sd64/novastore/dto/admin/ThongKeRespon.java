package com.sd64.novastore.dto.admin;

import com.sd64.novastore.model.BillDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ThongKeRespon {
    Integer getId();


    String getCustomerName();


    String getAddress();


    String getPhoneNumber();


    String getNote();


    Date getOrderDate();


    Date getConfirmationDate();


    Date getShippingDate();


    Date getReceivedDate();


    Date getCompletionDate();


    Date getPaymentDate();


    BigDecimal getPrice();


    BigDecimal getShippingFee();


    BigDecimal getDiscountAmount();


    BigDecimal getTotalPrice();


    Date getCreateDate();


    Date getUpdateDate();


    Integer getStatus();


    Integer getAccountId();


    Integer getCustomerId();

}
