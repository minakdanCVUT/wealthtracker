package com.userservice.service;

import com.core.exceptions.UserAlreadyExistsException;
import com.userservice.dto.CreateUserRequest;
import com.userservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final QueryService service;
    private final UserMapper mapper;

    @Override
    public void createUser(CreateUserRequest request) throws UserAlreadyExistsException {
        User user = mapper.toEntity(request);
        service.saveUser(user);
    }
}
