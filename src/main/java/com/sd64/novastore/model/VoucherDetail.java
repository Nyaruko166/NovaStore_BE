package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "VoucherDetail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VoucherDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "PriceAfter")
    private BigDecimal priceAfter;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "VoucherId", referencedColumnName = "Id")
    private VoucherDetail voucherDetail;

    @ManyToOne
    @JoinColumn(name = "BillId", referencedColumnName = "Id")
    private Bill bill;
}
