package com.BTA.SmartPrep.mapper;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UpdateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.User;

public interface UserMapper {
    CreateUserRequest fromDto(CreateUserRequestDto dto);
    UpdateUserRequest fromDto(UpdateUserRequestDto dto);
    UserDto toDto(User user);
}
