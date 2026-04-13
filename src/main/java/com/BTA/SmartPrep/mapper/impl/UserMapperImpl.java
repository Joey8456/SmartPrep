package com.BTA.SmartPrep.mapper.impl;

import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.CreateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UpdateUserRequestDto;
import com.BTA.SmartPrep.domain.dto.UserDto;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component //Marks as bean
public class UserMapperImpl implements UserMapper {
    @Override
    public CreateUserRequest fromDto(CreateUserRequestDto dto) {
        return new CreateUserRequest(
                dto.username(),
                dto.email(),
                dto.passhash()
        );
    }

    @Override
    public UpdateUserRequest fromDto(UpdateUserRequestDto dto) {
        return new UpdateUserRequest(
                dto.username(),
                dto.email(),
                dto.passhash()
        );
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUserName(),
                user.getEmail()
        );
    }
}
