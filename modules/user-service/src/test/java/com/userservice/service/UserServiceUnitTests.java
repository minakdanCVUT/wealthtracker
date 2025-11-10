package com.userservice.service;

import com.core.exceptions.NullableViolation;
import com.core.exceptions.UniquenessViolation;
import com.core.exceptions.password.FewerNumbersThanRequiredException;
import com.userservice.dto.ChangePasswordRequest;
import com.userservice.dto.CreateUserRequest;
import com.userservice.model.User;
import com.userservice.service.implementations.PasswordStrengthCheckerImpl;
import com.userservice.service.implementations.QueryServiceImpl;
import com.userservice.service.implementations.UserServiceImpl;
import com.userservice.service.interfaces.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTests {
    @Mock
    private QueryServiceImpl queryService;

    @Mock
    private UserMapper mapper;

    @Mock
    private PasswordStrengthCheckerImpl checker;

    @InjectMocks
    private UserServiceImpl service;

    @Nested
    class CreateUserTests{
        private final String NAME = "testName", SURNAME = "testSurname", USERNAME = "testUsername", EMAIL = "testEmail", PASSWORD = "testPassword";
        private CreateUserRequest request;
        private User mapperResult;

        @AfterEach
        void verification(){
            verify(mapper).toEntity(request);
            verify(checker).check(PASSWORD);
            verify(queryService).saveUser(mapperResult);
        }

        @Nested
        class CreateUserTestsWithoutNull{
            @BeforeEach
            void setUp(){
                request = new CreateUserRequest(NAME, SURNAME, USERNAME, EMAIL, PASSWORD, new ArrayList<>());
                mapperResult = new User();
                mapperResult.setPassword(PASSWORD);

                doNothing().when(checker).check(PASSWORD);
                when(mapper.toEntity(request)).thenReturn(mapperResult);
            }

            @Test
            void createUser_WithValidData_ShouldCreateUser(){
                // act
                service.createUser(request);
            }

            @Test
            void createUser_WithDuplicateData_ShouldThrowUniquenessViolation() {
                // arrange
                doThrow(UniquenessViolation.class).when(queryService).saveUser(mapperResult);

                //act
                assertThrows(UniquenessViolation.class, () -> service.createUser(request));

            }
        }

        @Nested
        class CreateUserTestsWithNull{
            @BeforeEach
            void setUp(){
                mapperResult = new User();
                mapperResult.setPassword(PASSWORD);

            }
            @Test
            void createUser_WithNullUsername_ShouldThrowNullableViolation(){
                //arrange
                request = new CreateUserRequest(NAME, SURNAME, null, EMAIL, PASSWORD, new ArrayList<>());
                when(mapper.toEntity(request)).thenReturn(mapperResult);
                doThrow(NullableViolation.class).when(queryService).saveUser(mapperResult);

                //act
                assertThrows(NullableViolation.class, () -> service.createUser(request));
            }

            @Test
            void createUser_WithNullEmail_ShouldThrowNullableViolation(){
                request = new CreateUserRequest(NAME, SURNAME, USERNAME, null, PASSWORD, new ArrayList<>());
                when(mapper.toEntity(request)).thenReturn(mapperResult);
                doThrow(NullableViolation.class).when(queryService).saveUser(mapperResult);

                // act
                assertThrows(NullableViolation.class, () -> service.createUser(request));
            }
        }
    }

    @Nested
    class UpdatePasswordTests{
        private final Long id = 1L;
        private final String oldPassword = "oldPassword", newPassword = "newPassword";
        private final ChangePasswordRequest request = new ChangePasswordRequest(id, oldPassword, newPassword);
        private final User user = new User();

        @BeforeEach
        void setUp(){
            user.setId(id);
            user.setPassword(oldPassword);
        }

        @Test
        void updatePassword_WithValidData_ShouldUpdatePassword(){
            //arrange
            when(queryService.findById(id)).thenReturn(user);
            doNothing().when(checker).check(newPassword);
            doNothing().when(queryService).saveUser(user);

            //act
            service.updatePassword(request);

            //assert
            verify(queryService).findById(id);
            verify(checker).check(newPassword);
            verify(queryService).saveUser(argThat(user1 -> user1.getPassword().equals(newPassword) && user1.getId().equals(id)));
        }

        @Test
        void updatePassword_WithInvalidID_ShouldThrowEntityNotFoundException(){
            // arrange
            doThrow(EntityNotFoundException.class).when(queryService).findById(id);

            // act
            assertThrows(EntityNotFoundException.class, () -> service.updatePassword(request));

            // assert
            verify(queryService).findById(id);
            verify(checker, never()).check(newPassword);
            verify(queryService, never()).saveUser(any());
        }

        @Test
        void updatePassword_WithOtherOldPassword_ShouldThrowIllegalArgumentException(){
            //arrange
            when(queryService.findById(id)).thenReturn(user);
            user.setPassword("otherPassword");

            //act
            assertThrows(IllegalArgumentException.class, () -> service.updatePassword(request));

            //assert
            verify(queryService).findById(id);
            verify(checker, never()).check(newPassword);
            verify(queryService, never()).saveUser(any());
        }

        @Test
        void updatePassword_WithInvalidPassword_ShouldThrowException(){
            //arrange
            when(queryService.findById(id)).thenReturn(user);
            doThrow(FewerNumbersThanRequiredException.class).when(checker).check(newPassword);

            //act
            assertThrows(FewerNumbersThanRequiredException.class, () -> service.updatePassword(request));

            //assert
            verify(queryService).findById(id);
            verify(checker).check(newPassword);
            verify(queryService, never()).saveUser(any());

        }
    }
}

