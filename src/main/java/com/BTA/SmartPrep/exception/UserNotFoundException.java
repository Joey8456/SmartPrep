package com.BTA.SmartPrep.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    private final UUID id;

    public UserNotFoundException(UUID id){
        super(String.format("Task With ID '%s' does not exist.", id));
        this.id = id;
    }

    public UUID getId(){
        return id;
    }
}
