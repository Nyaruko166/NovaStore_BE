package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Table(name = "Bill")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "Address")
    private String address;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Note")
    private String note;

    @Column(name = "OrderDate")
    private Date orderDate;

    @Column(name = "ConfirmationDate")
    private Date confirmationDate;

    @Column(name = "ShippingDate")
    private Date shippingDate;

    @Column(name = "ReceivedDate")
    private Date receivedDate;

    @Column(name = "CompletionDate")
    private Date completionDate;

    @Column(name = "PaymentDate")
    private Date paymentDate;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "ShippingFee")
    private BigDecimal shippingFee;

    @Column(name = "DiscountAmount")
    private BigDecimal discountAmount;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CustomerId", referencedColumnName = "Id")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
    private List<BillDetail> billDetails;

    public String getStatusName(){
        if (this.status == 10){
            return "Chờ xác nhận";
        } else if (this.status == 3){
            return "Đã xác nhận";
        } else if (this.status == 2){
            return "Đang giao hàng";
        } else if (this.status == 1){
            return "Đã hoàn thành";
        } else if (this.status == 0){
            return "Đã huỷ";
        } else {
            return null;
        }
    }
}
