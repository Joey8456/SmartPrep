package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.dto.ProblemDto;

public interface ProblemService {
    public ProblemDto getRandomProblemByCategory(int categoryId);
}
