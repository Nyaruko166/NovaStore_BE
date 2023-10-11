package com.sd64.novastore.request;

import com.sd64.novastore.model.Bill;
import com.sd64.novastore.model.BillDetail;
import com.sd64.novastore.model.ProductDetail;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class BillDetailRequest {
    @NotNull(message = "quantity khong duoc de trong")
    private String quantity;

    @NotNull(message = "price khong duoc de trong")
    private String price;


    private String status;


    private String billId;


    private String productDetailId;

    public BillDetail map(BillDetail billDetail) {
        billDetail.setQuantity(Integer.valueOf(this.getQuantity()));
        billDetail.setPrice(BigDecimal.valueOf(Long.parseLong(this.getPrice())));
        billDetail.setStatus(Integer.valueOf(this.getStatus()));
        billDetail.setBill(Bill.builder().id(Integer.valueOf(this.getBillId())).build());
        billDetail.setProductDetail(ProductDetail.builder().id(Integer.valueOf(this.getProductDetailId())).build());
        return billDetail;
    }
}
