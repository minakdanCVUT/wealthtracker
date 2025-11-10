package com.userservice.service.interfaces;

import com.userservice.dto.ChangeEmailRequest;
import com.userservice.dto.ChangeFullNameRequest;
import com.userservice.dto.ChangePasswordRequest;
import com.userservice.dto.CreateUserRequest;

import java.util.List;

public interface UserService {
    // TODO написать методы для блокировки и разблокировки пользователя
    void createUser(CreateUserRequest request) throws RuntimeException;

    void updatePassword(ChangePasswordRequest request);

    void updateEmail(ChangeEmailRequest request);

    void assignRoles(Long userId, List<String> roles);

    void changeFullName(ChangeFullNameRequest request);
}
