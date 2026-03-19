package com.BTA.SmartPrep.domain.dto;


//TODO GET NOT BLANK AND LENGTH TO WORK FOR EASY VALIDATION
public record CreateUserRequestDto(
        String username,
        String email,
        String passwordHash) {

}
