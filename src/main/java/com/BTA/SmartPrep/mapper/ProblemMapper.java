package com.BTA.SmartPrep.mapper;
import com.BTA.SmartPrep.domain.dto.ProblemDto;
import com.BTA.SmartPrep.domain.entity.Problem;


public interface ProblemMapper {
    ProblemDto toDto(Problem problem);
}
