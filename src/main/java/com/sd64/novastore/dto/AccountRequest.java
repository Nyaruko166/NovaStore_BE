package com.sd64.novastore.dto;


import com.sd64.novastore.model.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;


@Getter
@Setter

public class AccountRequest {
    @NotBlank(message = "Name khong duoc de trong")
    private String name;

//    @PastOrPresent(message = "Ngay birthday nho hon hoac bang ngay hien tai")
    @NotNull(message = "Ngay birthday khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String birthday;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "Email khong hop le")
    @NotBlank(message = "Emial khong duoc de trong")
    private String email;


    @Pattern(regexp = "^\\d{9,15}$", message = "So dien thoai khong hop le")
    @NotBlank(message = "So dien thoai khong duoc de trong")
    private String phoneNumber;

    @NotBlank(message = "Name khong duoc de trong")
    private String password;

    @NotBlank(message = "Gender khong duoc de trong")
    private String gender;


    private String avatar;

//    @PastOrPresent(message = "Ngay createDate nho hon hoac bang ngay hien tai")
    @NotNull(message = "Ngay createDate khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createDate;

//    @PastOrPresent(message = "Ngay createDate nho hon hoac bang ngay hien tai")
    @NotNull(message = "Ngay createDate khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String updateDate;


    private String status;

    public Account map(Account account) {
        account.setName(this.getName());
        account.setBirthday(Date.from(Instant.parse((this.getBirthday()))));
        account.setEmail(this.getEmail());
        account.setPhoneNumber(this.getPhoneNumber());
        account.setPassword(this.getPassword());
        account.setGender(Boolean.valueOf(this.getGender()));
        account.setAvatar(this.getAvatar());
        account.setCreateDate(Date.from(Instant.parse(this.getCreateDate())));
        account.setUpdateDate(Date.from(Instant.parse(this.getUpdateDate())));
        account.setStatus(Integer.valueOf(this.getStatus()));
        return account;
    }

}
