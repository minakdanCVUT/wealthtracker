package com.userservice.service;

import com.core.exceptions.UserAlreadyExistsException;
import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryServiceUnitTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private QueryService service;

    private final String USERNAME = "testUsername";

    @Nested
    class SaveUserTests{
        @Test
        void saveUser_WithValidData_ShouldSaveUser(){

        }
    }
}
