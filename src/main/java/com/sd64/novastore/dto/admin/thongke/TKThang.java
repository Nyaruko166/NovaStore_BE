package com.sd64.novastore.dto.admin.thongke;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public interface TKThang {
    Integer getSoLuongThanhCong();

    BigDecimal getDoanhThu();

    Integer getSoLuongHuy();

}
