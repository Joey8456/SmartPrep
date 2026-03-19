package com.BTA.SmartPrep.domain;

public record UpdateUserRequest(
        String username,
        String email,
        String passhash
) {

}
