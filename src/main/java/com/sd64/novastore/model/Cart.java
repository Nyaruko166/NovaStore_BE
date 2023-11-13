package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "Cart")
@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "AccountId", referencedColumnName = "Id")
    private Customer customer;

    @Column(name = "TotalPrice")
    private BigDecimal totalPrice;

    @Column(name = "TotalItems")
    private Integer totalItems;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "cart")
    private Set<CartDetail> cartDetails;

    public Cart(){
        this.cartDetails = new HashSet<>();
        this.totalItems = 0;
        this.totalPrice = BigDecimal.valueOf(0);
    }

}
