package com.BTA.SmartPrep.mapper;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.user.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.user.UpdateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.user.UserDto;
import com.BTA.SmartPrep.domain.entity.User;

public interface UserMapper {
    CreateUserRequest fromDto(CreateUserRequestDto dto);
    UpdateUserRequest fromDto(UpdateUserRequestDto dto);
    UserDto toDto(User user);
}
