package com.BTA.SmartPrep.mapper;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.UpdateProfficiencyRequest;
import com.BTA.SmartPrep.domain.dto.profficiency.CreateProfficiencyRequestDto;
import com.BTA.SmartPrep.domain.dto.profficiency.ProfficiencyDto;
import com.BTA.SmartPrep.domain.dto.profficiency.UpdateProfficiencyRequestDto;
import com.BTA.SmartPrep.domain.entity.Proficiency;

public interface ProfficiencyMapper {
        CreateProfficiencyRequest fromDto(CreateProfficiencyRequestDto dto);
        UpdateProfficiencyRequest fromDto(UpdateProfficiencyRequestDto dto);
        ProfficiencyDto toDto(Proficiency proficiency);
}
