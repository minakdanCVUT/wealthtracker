package com.userservice.dto;

public record ChangeEmailRequest(
        Long userId,
        String oldEmail,
        String newEmail
) {
}
