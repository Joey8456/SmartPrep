package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.dto.user.UserDto;
import com.BTA.SmartPrep.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserRequest request);
    Optional<UserDto> getUser(UUID userId);
    User getUserLogin(String email, String passhash);

    //TODO
//    User updateUser(String userId, UpdateUserRequest request);
}

