package com.sd64.novastore.service.impl;

import com.sd64.novastore.model.Category;
import com.sd64.novastore.model.Role;
import com.sd64.novastore.repository.RoleRepository;
import com.sd64.novastore.request.RoleRequest;
import com.sd64.novastore.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAllByAndStatusOrderByIdDesc(1);
    }

    @Override
    public Page<Role> getPage(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return roleRepository.findAllByAndStatusOrderByIdDesc(pageable, 1);
    }

    @Override
    public Role add(RoleRequest roleRequest) {
        Role role = roleRequest.map(new Role());
        return roleRepository.save(role);
    }

    @Override
    public Role update(RoleRequest roleRequest, Integer id) {
        Optional<Role> optional = roleRepository.findById(id);
        return optional.map(o -> {
            o.setId(id);
            o.setStatus(roleRequest.getStatus());
            o.setName(roleRequest.getName());
            return roleRepository.save(o);
        }).orElse(null);
    }

    @Override
    public Boolean delete(Integer id) {
        Optional<Role> optional = roleRepository.findById(id);
        return optional.map(o -> {
            o.setStatus(0);
            roleRepository.save(o);
            return true;
        }).orElse(false);
    }

    @Override
    public Page<Role> search(String name, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        return roleRepository.findAllByNameContainsAndStatusOrderByIdDesc(name, 1, pageable);
    }
}
