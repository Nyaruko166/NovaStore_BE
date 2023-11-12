package com.sd64.novastore.service.impl.user;

import com.sd64.novastore.model.Color;
import com.sd64.novastore.repository.user.UserColorRepository;
import com.sd64.novastore.service.user.UserColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserColorServiceImpl implements UserColorService {
    @Autowired
    private UserColorRepository userColorRepository;

    @Override
    public List<Color> getColorByProductId(Integer id) {
        return userColorRepository.getColorByProductId(id);
    }
}
