package com.BTA.SmartPrep.service;

import java.io.IOException;
import java.nio.file.Path;

public interface SolutionService {
    String solutionGrade(String codeString, long problemId, String userId, int categoryId);
    Path writeSourceFile(String codeString) throws IOException;
    void compile(Path sourceFile);
    Class<?> loadSolutionClass(Path sourceFile) throws Exception;
    Object execute(Class<?> solutionClass, String methodName, Object... args) throws Exception
            ;
}
