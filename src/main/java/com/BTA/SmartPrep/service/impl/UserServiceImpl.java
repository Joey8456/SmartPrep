package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.exception.UserNotFoundException;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service //Marks UserServiceImpl as a bean, and injects any dependencies needed.
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(CreateUserRequest request) {
        User user = new User(null, request.userName(), request.email(), request.passHash() );
        userRepository.insertUser("test", "test@test.com", "123");
        return user;
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(UUID user_Id, UpdateUserRequest request) {
        User user = userRepository.findById(user_Id).orElseThrow(() -> new UserNotFoundException(user_Id));

        user.setUserName(request.username());
        user.setEmail(request.email());
        user.setPass_hash(request.passHash());

        return userRepository.save(user);
    }

}
