package com.userservice.dto;

public record ChangeFullNameRequest(
        Long userId,
        String name,
        String surname
) {
}
