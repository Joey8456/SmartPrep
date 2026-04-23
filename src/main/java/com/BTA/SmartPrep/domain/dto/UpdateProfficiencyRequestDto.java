package com.BTA.SmartPrep.domain.dto;

public record UpdateProfficiencyRequestDto(
        String userId,
        int categoryId,
        int proficiency
)
{

}

