package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.dto.problem.ProblemDto;

public interface ProblemService {
    public ProblemDto getRandomProblemByCategory(int categoryId, String userId);
}
