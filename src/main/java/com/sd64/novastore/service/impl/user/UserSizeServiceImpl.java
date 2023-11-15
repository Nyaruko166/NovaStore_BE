package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.user.UserSizeRepository;
import com.sd64.novastore.service.user.UserSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSizeServiceImpl implements UserSizeService {
    @Autowired
    private UserSizeRepository userSizeRepository;

    @Override
    public List<Size> getSizeByProductId(Integer id) {
        return userSizeRepository.getSizeByProductId(id);
    }
}
