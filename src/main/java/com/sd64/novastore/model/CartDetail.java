package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "CartDetail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Quantity")
    private Integer quantity;

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
    @JoinColumn(name = "CartId", referencedColumnName = "Id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "ProductDetailId", referencedColumnName = "Id")
    private ProductDetail productDetail;
}
