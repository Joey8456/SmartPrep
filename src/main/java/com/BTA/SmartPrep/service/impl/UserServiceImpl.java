package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.stereotype.Service;

@Service //Marks UserServiceImpl as a bean, and injects any dependencies needed.
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        User user = new User(null, request.userName(), request.email(), request.passHash() );
        return userRepository.save(user);
    }
}
