package com.userservice.service.implementations;

import com.core.exceptions.UserAlreadyExistsException;
import com.userservice.dto.ChangeEmailRequest;
import com.userservice.dto.ChangeFullNameRequest;
import com.userservice.dto.ChangePasswordRequest;
import com.userservice.dto.CreateUserRequest;
import com.userservice.model.User;
import com.userservice.service.interfaces.QueryService;
import com.userservice.service.interfaces.UserMapper;
import com.userservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final QueryService service;
    private final UserMapper mapper;
    private final PasswordStrengthCheckerImpl checker;

    //TODO написать кафка сервис для отправки писем для подтверждения аккаунта

    @Override
    public void createUser(CreateUserRequest request) throws UserAlreadyExistsException {
        User user = mapper.toEntity(request);
        checker.check(user.getPassword());
        service.saveUser(user);
    }

    @Override
    public void updatePassword(ChangePasswordRequest request) throws IllegalArgumentException{
        var user = service.findById(request.userId());
        if(!user.getPassword().equals(request.oldPassword())){
            throw new IllegalArgumentException("Incorrect old password");
        }
        checker.check(request.newPassword());
        user.setPassword(request.newPassword());
        service.saveUser(user);
    }

    @Override
    public void updateEmail(ChangeEmailRequest request) {
        var user = service.findById(request.userId());
        if(!user.getEmail().equals(request.oldEmail())){
            throw new IllegalArgumentException("Incorrect old email");
        }
        //TODO дописать сюда опять же отправку уведомления на новую почту и после этого подтверждать ее, так пока оставим
        user.setEmail(request.newEmail());
        service.saveUser(user);
    }

    @Override
    public void assignRoles(Long userId, List<String> roles) {
        var user = service.findById(userId);
        user.getRoles().addAll(roles);
        service.saveUser(user);
    }

    @Override
    public void changeFullName(ChangeFullNameRequest request) {
        var user = service.findById(request.userId());
        user.setName(request.name());
        user.setSurname(request.surname());
        service.saveUser(user);
    }
}
