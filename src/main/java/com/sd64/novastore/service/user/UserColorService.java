package com.sd64.novastore.service.user;

import com.sd64.novastore.model.Color;

import java.util.List;

public interface UserColorService {
    List<Color> getColorByProductId(Integer id);
}
