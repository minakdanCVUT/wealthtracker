package com.userservice.service.interfaces;

import com.userservice.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface QueryService {
    @Transactional
    void saveUser(User user);

    @Transactional(readOnly = true)
    User findById(Long userId);
}
