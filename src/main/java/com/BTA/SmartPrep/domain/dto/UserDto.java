package com.BTA.SmartPrep.domain.dto;


public record UserDto(
        String userId,
        String username,
        String email,
        String passhash
) {

}
