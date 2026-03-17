package com.BTA.SmartPrep.domain.dto;


import java.util.UUID;

public record UserDto(
        UUID userId,
        String userName,
        String email,
        String pashHash
) {

}
