package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "Voucher")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Code")
    private String code;

    @Column(name = "Name")
    private String name;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Type")
    private Boolean type;

    @Column(name = "Value")
    private BigDecimal value;

    @Column(name = "MinimumPrice")
    private BigDecimal minimumPrice;

    @Column(name = "MaximumDiscount")
    private BigDecimal maximumDiscount;

    @Column(name = "StartDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    @Column(name = "EndDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;
}
