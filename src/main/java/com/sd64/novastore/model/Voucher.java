package com.sd64.novastore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.math.BigDecimal;
import java.util.Date;

@Table(name = "Voucher")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;


    @Column(name = "Code")
    private String code;



    @Column(name = "Name")
    private String name;


    @Column(name = "Quantity")
    private Integer quantity;



    @Column(name = "Value")
    private BigDecimal value;


    @Column(name = "MinimumPrice")
    private BigDecimal minimumPrice;



    @Column(name = "StartDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date startDate;


    @Column(name = "EndDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date endDate;

    @Column(name = "CreateDate")
    private Date createDate;

    @Column(name = "UpdateDate")
    private Date updateDate;

    @Column(name = "Status")
    private Integer status;

    public String getStatusName(){
        if (this.status == 1){
            return "Hoạt động";
        } else if (this.status == 2) {
            return "Đang chờ";
        }else {
            return "Đã hủy";
        }
    }
    public void updateStatus() {
        Date currentDate = new Date();

        if (currentDate.before(startDate)) {
            status = 2;
        } else if (currentDate.equals(startDate) || currentDate.after(startDate)) {
            status = 1;
        }
    }

}
