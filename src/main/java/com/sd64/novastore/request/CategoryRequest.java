package com.sd64.novastore.request;

import com.sd64.novastore.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
public class CategoryRequest {
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

    public Category map(Category category) {
        category.setName(this.getName());
        category.setCreateDate(Date.from(Instant.parse(this.getCreateDate())));
        category.setUpdateDate(Date.from(Instant.parse(this.getUpdateDate())));
        category.setStatus(Integer.valueOf(this.getStatus()));
        return category;
    }
}
