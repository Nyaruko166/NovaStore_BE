package com.sd64.novastore.service;

import com.sd64.novastore.model.Role;
import com.sd64.novastore.model.Staff;
import com.sd64.novastore.request.RoleRequest;
import com.sd64.novastore.request.StaffRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {

    List<Staff> getAll();

    Page<Staff> getPage(int page);

    Staff add(StaffRequest staffRequest);

    Staff update(StaffRequest staffRequest, Integer id);

    Boolean delete(Integer id);
}
