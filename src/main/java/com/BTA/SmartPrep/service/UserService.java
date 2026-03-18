package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserRequest request);
    List<User> listUsers();
    User updateUser(UUID userId, UpdateUserRequest request);
}

