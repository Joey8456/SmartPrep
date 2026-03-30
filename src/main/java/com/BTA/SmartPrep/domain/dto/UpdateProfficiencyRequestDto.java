package com.BTA.SmartPrep.domain.dto;

public record UpdateProfficiencyRequestDto(
        String user_ID,
        int category_ID,
        int proficiency
)
{

}

