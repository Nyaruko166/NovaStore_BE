package com.sd64.novastore.request;

import com.sd64.novastore.model.Size;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SizeRequest {

    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private Date createDate;

    private Date updateDate;

    private Integer status = 1;

    public Size map(Size size) {
        return Size.builder().id(this.getId())
                .name(this.getName())
                .createDate(new Date())
                .updateDate(new Date())
                .status(this.getStatus())
                .build();
    }
}
