package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.dto.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.UserMapper;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController //Tells spring look at this for API
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }
//TODO @Valid needed to validate need dependency

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody CreateUserRequestDto createUserRequestDto
    ){
        CreateUserRequest createUserRequest = userMapper.fromDto(createUserRequestDto);
        User user = userService.createUser(createUserRequest);
        UserDto createdUserDto = userMapper.toDto(user);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }
}
