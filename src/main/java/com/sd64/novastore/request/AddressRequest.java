package com.sd64.novastore.request;


import com.sd64.novastore.model.Account;
import com.sd64.novastore.model.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter

public class AddressRequest {
    @NotBlank(message = "Name khong duoc de trong")
    private String city;

    @NotBlank(message = "district khong duoc de trong")
    private String district;

    @NotBlank(message = "ward khong duoc de trong")
    private String ward;

    @NotBlank(message = "specificAddress khong duoc de trong")
    private String specificAddress;


    private String createDate;


    private String updateDate;


    private String status;

//    @NotNull(message = "accountID khong duoc de trong")
    private String accountID;

    public Address map(Address address) {
        address.setCity(this.getCity());
        address.setDistrict(this.getDistrict());
        address.setWard(this.getWard());
        address.setSpecificAddress(this.getSpecificAddress());
        address.setCreateDate(Date.from(Instant.parse(this.getCreateDate())));
        address.setUpdateDate(Date.from(Instant.parse(this.getUpdateDate())));
        address.setStatus(Integer.valueOf(this.getStatus()));
        address.setAccount(Account.builder().id(Integer.valueOf(this.getAccountID())).build());
        return address;
    }
}
