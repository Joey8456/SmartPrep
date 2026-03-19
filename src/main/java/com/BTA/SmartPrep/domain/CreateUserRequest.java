package com.BTA.SmartPrep.domain;

//This allows us to create user
public record CreateUserRequest(
        String username,
        String email,
        String passhash
){}
