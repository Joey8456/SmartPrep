package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.CreateProfficiencyRequest;
import com.BTA.SmartPrep.domain.UpdateProfficiencyRequest;
import com.BTA.SmartPrep.domain.dto.profficiency.ProfficiencyDto;
import com.BTA.SmartPrep.domain.entity.Proficiency;

public interface ProfficiencyService {

    Proficiency createProfficiency(CreateProfficiencyRequest request);
    Proficiency updateProfficiency(UpdateProfficiencyRequest request);
    ProfficiencyDto getProfficiency(String userId, int categoryID);
}
