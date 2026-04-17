package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.ProblemDto;
import com.BTA.SmartPrep.domain.dto.UpdateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.UserMapper;
import com.BTA.SmartPrep.repository.UserRepository;
import com.BTA.SmartPrep.service.ProfficiencyService;
import com.BTA.SmartPrep.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    @GetMapping("/{email}/{passhash}")
    public ResponseEntity<UserDto> getUser(
            @PathVariable String email,
            @PathVariable String passhash
    ){
        User user = userService.getUserLogin(email,passhash);
        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }
    //TODO implement this
//    @PutMapping(path = "/{userId}")
//    public ResponseEntity<UserDto> updateUser(
//            @PathVariable UUID userId,
//    @RequestBody UpdateUserRequestDto updateUserRequestDto
//    )33
//        UpdateUserRequest updateUserRequest = userMapper.fromDto(updateUserRequestDto);
//        User user= userService.updateUser(userId,updateUserRequest);
//        UserDto userDto = userMapper.toDto(user);
//        return ResponseEntity.ok(userDto);
//    }
}
