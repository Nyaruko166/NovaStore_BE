package com.sd64.novastore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "Promotion")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @NotBlank(message = "Code Khong duoc de trong")
    @Size(max = 50, message = "Code khong duoc vuot qua 50 ky tu")
    @Column(name = "Code")
    private String code;

    @NotBlank(message = "Name khong duoc de trong")
    @Size(max = 50, message = "Name khong duoc vuot qua 50 ky tu")
    @Column(name = "Name")
    private String name;

    @NotNull(message = "Value khong duoc de trong")
    @Min(value = 10000, message = "Value phai lon hon hoac bang 10000")
    @Max(value = 1000000000, message = "Value phai nho hon hoac bang 1000000000")
    @Column(name = "Value")
    private BigDecimal value;

    @NotNull(message = "StartDate khong duoc de trong")
    @Column(name = "StartDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;

    @NotNull(message = "EndDate khong duoc de trong")
    @Column(name = "EndDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

}
