package com.userservice.service;

import com.userservice.dto.CreateUserRequest;

public interface UserService {
    void createUser(CreateUserRequest request) throws RuntimeException;
}
