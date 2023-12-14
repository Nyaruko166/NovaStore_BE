package com.sd64.novastore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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


    @Column(name = "Code")
    private String code;


    @Column(name = "Name")
    private String name;


    @Column(name = "Value")
    private BigDecimal value;


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

    @OneToMany(mappedBy = "promotion")
    private List<PromotionDetail> promotionDetails;

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
