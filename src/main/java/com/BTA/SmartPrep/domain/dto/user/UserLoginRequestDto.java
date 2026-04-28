package com.BTA.SmartPrep.domain.dto.user;

public record UserLoginRequestDto(
        String email,
        String password
) {
}
