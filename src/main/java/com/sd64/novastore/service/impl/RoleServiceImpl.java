package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Role;
import com.sd64.novastore.repository.RoleRepository;
import com.sd64.novastore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAllByAndStatusOrderByUpdateDateDesc(1);
    }

    @Override
    public Page<Role> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return roleRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Role add(Role role) {
        role.setStatus(1);
        role.setCreateDate(new Date());
        role.setUpdateDate(new Date());
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role, Integer id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            Role updateRole = optional.get();
            role.setId(id);
            role.setStatus(updateRole.getStatus());
            role.setCreateDate(updateRole.getCreateDate());
            role.setUpdateDate(new Date());
            return roleRepository.save(role);
        } else {
            return null;
        }
    }

    @Override
    public Role getOne(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public Role delete(Integer id) {
        Optional<Role> optional = roleRepository.findById(id);
        if (optional.isPresent()) {
            Role role = optional.get();
            role.setStatus(0);
            return roleRepository.save(role);
        } else {
            return null;
        }
    }

    @Override
    public Page<Role> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return roleRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
