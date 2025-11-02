package com.userservice.service;

import com.userservice.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface QueryService {

    @Transactional
    void saveUser(User user);
}
