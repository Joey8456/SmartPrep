package com.BTA.SmartPrep.domain.dto.profficiency;

public record CreateProfficiencyRequestDto(
    String user_ID,
    int category_ID,
    int answer){
}
