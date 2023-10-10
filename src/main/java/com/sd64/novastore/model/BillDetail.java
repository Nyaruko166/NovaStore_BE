package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "BillDetail")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Quantity")
    private Integer quantity;

    @Column(name = "Price")
    private BigDecimal price;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "BillId", referencedColumnName = "Id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "ProductDetailId", referencedColumnName = "Id")
    private ProductDetail productDetail;
}
