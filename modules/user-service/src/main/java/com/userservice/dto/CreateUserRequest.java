package com.userservice.dto;

import java.util.List;

public record CreateUserRequest(
        String name,
        String surname,
        String username,
        String email,
        String password,
        List<String> roles
) {
}
