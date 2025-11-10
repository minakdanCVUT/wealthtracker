package com.userservice.service;

import com.userservice.model.User;
import com.userservice.repository.UserRepository;
import com.userservice.service.implementations.QueryServiceImpl;
import com.userservice.service.interfaces.QueryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QueryServiceUnitTest {
    @Mock
    private UserRepository repository;

    @InjectMocks
    private QueryServiceImpl service;

    private final String USERNAME = "testUsername";

    @Nested
    class SaveUserTests{
        @Test
        void saveUser_WithValidData_ShouldSaveUser(){

        }
    }

    @Nested
    class FindByIdTests {
        @Test
        void findById_WithValidId_ShouldReturnUser() {
            //arrange
            User user = new User();
            user.setId(1L);
            when(repository.findById(1L)).thenReturn(Optional.of(user));

            //act
            service.findById(1L);

            //assert
            verify(repository).findById(1L);
        }

        @Test
        void findById_WithInvalidId_ShouldThrowEntityNotFoundException(){
            //arrange
            when(repository.findById(1L)).thenReturn(Optional.empty());

            //act
            assertThrows(EntityNotFoundException.class, () -> service.findById(1L));

            // assert
            verify(repository).findById(1L);
        }
    }
}
