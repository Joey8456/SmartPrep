package com.BTA.SmartPrep.domain;

public record UpdateProfficiencyRequest(
        String user_ID,
        int category_ID,
        int proficiency
) {
}
