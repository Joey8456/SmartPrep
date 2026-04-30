package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.dto.problem.SolutionSubmissionDto;

import java.io.IOException;
import java.nio.file.Path;

public interface SolutionService {
    SolutionSubmissionDto solutionGrade(String codeString, long problemId, String userId, int categoryId);
    String solutionRunGrade(String codeString,long problemId,String userId,int categoryId);
    Path writeSourceFile(String codeString) throws IOException;
    void compile(Path sourceFile);
    Class<?> loadSolutionClass(Path sourceFile) throws Exception;
    Object execute(Class<?> solutionClass, String methodName, Object... args) throws Exception
            ;
}
