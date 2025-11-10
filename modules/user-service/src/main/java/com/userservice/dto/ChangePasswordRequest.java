package com.userservice.dto;

public record ChangePasswordRequest(
        Long userId,
        String oldPassword,
        String newPassword
) {
}
