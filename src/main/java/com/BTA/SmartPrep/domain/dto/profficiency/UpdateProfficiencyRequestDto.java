package com.BTA.SmartPrep.domain.dto.profficiency;

public record UpdateProfficiencyRequestDto(
        String userId,
        int categoryId,
        int proficiency
)
{

}

