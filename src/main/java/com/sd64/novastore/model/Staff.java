package com.sd64.novastore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Table(name = "Staff")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Birthday")
    private Date birthday;

    @Column(name = "Gender")
    private Boolean gender;

    @Column(name = "Address")
    private String address;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "Email")
    private String Email;

    @Column(name = "Passsword")
    private String password;

    @ManyToOne
    @JoinColumn(name = "RoleId", referencedColumnName = "Id")
    private Role role;
}
