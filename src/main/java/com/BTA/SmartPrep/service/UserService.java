package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.entity.User;

public interface UserService {

    User createUser(CreateUserRequest request);

}
