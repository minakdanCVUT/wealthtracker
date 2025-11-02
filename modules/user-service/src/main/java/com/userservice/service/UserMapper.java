package com.userservice.service;

import com.userservice.dto.CreateUserRequest;
import com.userservice.dto.UserDTO;
import com.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);

    User toEntity(CreateUserRequest request);
}
