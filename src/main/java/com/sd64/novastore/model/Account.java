package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Table(name = "Account")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Birthday")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @Column(name = "Email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Password")
    private String password;

    @Column(name = "Avatar")
    private String avatar;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "RoleId", referencedColumnName = "Id")
    private Role role;
}
