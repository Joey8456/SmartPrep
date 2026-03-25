package com.BTA.SmartPrep.domain;

public record CreateProfficiencyRequest(
        String user_ID,
        int category_ID,
        int proficiency
) {
}
