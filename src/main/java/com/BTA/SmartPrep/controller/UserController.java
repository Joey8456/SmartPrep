package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.dto.user.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.user.UserDto;
import com.BTA.SmartPrep.domain.dto.user.UserLoginRequestDto;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.UserMapper;
import com.BTA.SmartPrep.service.ProfficiencyService;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController //Tells spring look at this for API
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final ProfficiencyService profficiencyService;

    public UserController(UserService userService, UserMapper userMapper, ProfficiencyService profficiencyService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.profficiencyService = profficiencyService;
    }
//TODO @Valid needed to validate need dependency

    @PostMapping
    public ResponseEntity<UserDto> createUser(
            @RequestBody CreateUserRequestDto createUserRequestDto
    ) {
        CreateUserRequest createUserRequest = userMapper.fromDto(createUserRequestDto);
        User user = userService.createUser(createUserRequest);
        UserDto createdUserDto = userMapper.toDto(user);
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Optional<UserDto>> getRandomProblemByCategory(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> getUser(
            @RequestBody UserLoginRequestDto userLoginRequestDto
    ){
        User user = userService.getUserLogin(userLoginRequestDto.email(), userLoginRequestDto.password());
        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
}
