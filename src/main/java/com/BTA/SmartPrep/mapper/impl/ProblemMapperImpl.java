package com.BTA.SmartPrep.mapper.impl;
import com.BTA.SmartPrep.domain.dto.problem.ProblemDto;
import com.BTA.SmartPrep.domain.entity.Problem;
import com.BTA.SmartPrep.mapper.ProblemMapper;
import org.springframework.stereotype.Component;

@Component
public class ProblemMapperImpl implements ProblemMapper {
    @Override
    public ProblemDto toDto(Problem problem) {
        return new ProblemDto(
                problem.getProblemId(),
                problem.getTitle(),
                problem.getPrompt(),
                problem.getExamples(),
                problem.getProblemDifficulty(),
                problem.getCategory(),
                problem.getStarterCode(),
                problem.getSampleTestCase(),
                problem.getMethodName(),
                problem.getReturnType(),
                problem.getParameterType(),
                problem.getExpectedOutput()
        );
    }
}
