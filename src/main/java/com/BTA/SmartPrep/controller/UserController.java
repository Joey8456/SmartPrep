package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UpdateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.UserMapper;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping
    public ResponseEntity<List<UserDto>> listUsers(){
        List<User> users = userService.listUsers();
        List<UserDto> userDtos = users.stream().map(userMapper::toDto).toList();
        return ResponseEntity.ok(userDtos);
    }

    //TODO implement this
//    @PutMapping(path = "/{userId}")
//    public ResponseEntity<UserDto> updateUser(
//            @PathVariable UUID userId,
//    @RequestBody UpdateUserRequestDto updateUserRequestDto
//    ){
//        UpdateUserRequest updateUserRequest = userMapper.fromDto(updateUserRequestDto);
//        User user= userService.updateUser(userId,updateUserRequest);
//        UserDto userDto = userMapper.toDto(user);
//        return ResponseEntity.ok(userDto);
//    }


    //BELOW ARE ALL TESTS.


    @RestController
    @RequestMapping("/test")
    public class TestController {

        @GetMapping
        public String test() {
            return "Backend is working";
        }
    }
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/add")
    public String addUser() {
        userRepository.insertUser("test", "test@test.com", "123");
        return "User saved";
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    }

