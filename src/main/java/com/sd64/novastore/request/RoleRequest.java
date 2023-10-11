package com.sd64.novastore.request;

import com.sd64.novastore.model.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleRequest {

    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    private Integer status = 1;

    public Role map(Role role) {
        return Role.builder().id(this.getId())
                .name(this.getName())
                .status(this.getStatus())
                .build();
    }
}
