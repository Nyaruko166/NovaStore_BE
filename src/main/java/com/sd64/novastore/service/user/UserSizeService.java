package com.sd64.novastore.service.user;

import com.sd64.novastore.model.Size;

import java.util.List;

public interface UserSizeService {
    List<Size> getSizeByProductId(Integer id);
}
