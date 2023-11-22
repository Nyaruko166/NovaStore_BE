package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "Address")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "CustomerName")
    private String customerName;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "City")
    private String city;

    @Column(name = "District")
    private String district;

    @Column(name = "Ward")
    private String ward;

    @Column(name = "SpecificAddress")
    private String specificAddress;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "AccountId", referencedColumnName = "Id")
    private Account account;
}
