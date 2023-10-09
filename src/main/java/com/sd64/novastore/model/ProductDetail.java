package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "ProductDetail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Description")
    private String description;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "ProductId", referencedColumnName = "Id")
    private Product product;
}
