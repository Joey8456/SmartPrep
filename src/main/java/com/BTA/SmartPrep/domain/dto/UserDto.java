package com.BTA.SmartPrep.domain.dto;


import java.util.UUID;

public record UserDto(
        String userId,
        String userName,
        String email,
        String pashHash
) {

}
