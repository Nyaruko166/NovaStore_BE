package com.sd64.novastore.request;

import com.sd64.novastore.model.Brand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter

public class BrandRequest {
    @NotBlank(message = "Name khong duoc de trong")
    private String name;

//    @PastOrPresent(message = "Ngay createDate nho hon hoac bang ngay hien tai")
    @NotNull(message = "Ngay createDate khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String createDate;

//    @PastOrPresent(message = "Ngay update Date nho hon hoac bang ngay hien tai")
    @NotNull(message = "Ngay createDate khong duoc de trong")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String updateDate;


    private String status;

    public Brand map(Brand brand) {
        brand.setName(this.getName());
        brand.setCreateDate(new Date());
        brand.setUpdateDate(new Date());
        brand.setStatus(1);
        return brand;
    }
}
