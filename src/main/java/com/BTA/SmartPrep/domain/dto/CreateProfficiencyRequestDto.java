package com.BTA.SmartPrep.domain.dto;

public record CreateProfficiencyRequestDto(
    String user_ID,
    int category_ID,
    int answer){
}
