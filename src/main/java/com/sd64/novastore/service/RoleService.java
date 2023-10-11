package com.sd64.novastore.service;

import com.sd64.novastore.model.Role;
import com.sd64.novastore.request.RoleRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Page<Role> getPage(int page);

    Role add(RoleRequest roleRequest);

    Role update(RoleRequest roleRequest, Integer id);

    Boolean delete(Integer id);
}
