package com.BTA.SmartPrep.mapper.impl;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.CreateUserRequest;
import com.BTA.SmartPrep.domain.UpdateProfficiencyRequest;
import com.BTA.SmartPrep.domain.UpdateUserRequest;
import com.BTA.SmartPrep.domain.dto.*;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.domain.entity.User;
import com.BTA.SmartPrep.mapper.ProfficiencyMapper;
import com.BTA.SmartPrep.mapper.UserMapper;
import org.springframework.stereotype.Component;

@Component //Marks as bean
public class ProfficiencyMapperImpl implements ProfficiencyMapper {
    @Override
    public CreateProfficiencyRequest fromDto(CreateProfficiencyRequestDto dto) {
        return new CreateProfficiencyRequest(
                dto.user_ID(),
                dto.category_ID(),
                1
        );
    }
    @Override
    public UpdateProfficiencyRequest fromDto(UpdateProfficiencyRequestDto dto) {
        return new UpdateProfficiencyRequest(
                dto.userId(),
                dto.categoryId(),
                dto.proficiency()
        );
    }
    @Override
    public ProfficiencyDto toDto(Proficiency proficiency) {
        return new ProfficiencyDto(
                proficiency.getId().getUserId(),
                proficiency.getId().getCategoryId(),
                proficiency.getProficiency()
        );
    }
}
