package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User createUser(CreateUserRequest request);
    Optional<UserDto> getUser(UUID userId);

    //TODO
//    User updateUser(String userId, UpdateUserRequest request);
}

