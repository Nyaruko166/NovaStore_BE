package com.sd64.novastore.service;

import com.sd64.novastore.model.Promotion;
import com.sd64.novastore.model.Role;
import com.sd64.novastore.request.RoleRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Page<Role> getPage(Integer page);

    Role add(Role role);

    Role update(Role role, Integer id);

    Role delete(Integer id);

    Role getOne(Integer id);

    Page<Role> search(String name, int page);
}
