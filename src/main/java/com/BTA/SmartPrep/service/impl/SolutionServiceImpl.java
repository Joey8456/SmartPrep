package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.entity.Problem;
import com.BTA.SmartPrep.domain.entity.TestCase;
import com.BTA.SmartPrep.repository.ProblemRepository;
import com.BTA.SmartPrep.repository.TestCaseRepository;
import com.BTA.SmartPrep.service.SolutionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class SolutionServiceImpl implements SolutionService {
    private final ProblemRepository problemRepository;
    private final TestCaseRepository testCaseRepository;
    private final ObjectMapper objectMapper;

    public SolutionServiceImpl(ProblemRepository problemRepository,
                               TestCaseRepository testCaseRepository,
                               ObjectMapper objectMapper) {
        this.problemRepository = problemRepository;
        this.testCaseRepository = testCaseRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void solutionGrade(String codeString, long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found for id: " + problemId));

        List<TestCase> testCases = testCaseRepository.findAllByProblemId(problemId);
        if (testCases.isEmpty()) {
            throw new RuntimeException("No test cases found for problem id: " + problemId);
        }

        try {
            Path sourceFile = writeSourceFile(codeString);
            compile(sourceFile);
            Class<?> solutionClass = loadSolutionClass(sourceFile);

            Class<?>[] parameterTypes = parseParameterTypes(problem.getParameterType());
            Method method = solutionClass.getDeclaredMethod(problem.getMethodName(), parameterTypes);
            method.setAccessible(true);

            int passedCount = 0;

            for (TestCase testCase : testCases) {
                Constructor<?> constructor = solutionClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object solutionInstance = constructor.newInstance();
                Object[] args = parseArguments(testCase.getInput_args(), parameterTypes);
                Object actual = method.invoke(solutionInstance, args);

                boolean passed = compareOutput(actual, testCase.getExpectedOutput(), method.getReturnType());
                if (passed) {
                    passedCount++;
                }

                System.out.println("Problem " + problemId +
                        " | TestCase " + testCase.getTestId() +
                        " | Passed: " + passed +
                        " | Expected: " + testCase.getExpectedOutput() +
                        " | Actual: " + formatActualOutput(actual));
            }

            System.out.println("Finished grading. Passed " + passedCount + " / " + testCases.size() + " test cases.");
        } catch (Exception e) {
            throw new RuntimeException("Grading failed: " + e.getMessage(), e);
        }
    }

    @Override
    public Path writeSourceFile(String codeString) throws IOException {
        Path tempDir = Files.createTempDirectory("smartprep_");
        Path sourceFile = tempDir.resolve("Solution.java");
        Files.writeString(sourceFile, codeString);
        return sourceFile;
    }

    @Override
    public void compile(Path sourceFile) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        if (compiler == null) {
            throw new RuntimeException("Java compiler not available. Make sure you are running with a JDK, not just a JRE.");
        }

        int result = compiler.run(null, null, null, sourceFile.toString());
        if (result != 0) {
            throw new RuntimeException("Compilation failed.");
        }
    }

    @Override
    public Class<?> loadSolutionClass(Path sourceFile) throws Exception {
        Path tempDir = sourceFile.getParent();

        URLClassLoader classLoader = URLClassLoader.newInstance(
                new URL[]{tempDir.toUri().toURL()}
        );

        return Class.forName("Solution", true, classLoader);
    }

    @Override
    public Object execute(Class<?> solutionClass, String methodName, Object... args) throws Exception {
        Constructor<?> constructor = solutionClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object solutionInstance = constructor.newInstance();
        Method method = findMatchingMethod(solutionClass, methodName, args.length);
        method.setAccessible(true);
        return method.invoke(solutionInstance, args);
    }

    public Object[] parseArguments(String inputArgsJson, Class<?>[] parameterTypes) throws IOException {
        JsonNode root = objectMapper.readTree(inputArgsJson);

        if (!root.isArray()) {
            throw new IllegalArgumentException("input_args must be a JSON array.");
        }

        if (root.size() != parameterTypes.length) {
            throw new IllegalArgumentException(
                    "Parameter count mismatch. Expected " + parameterTypes.length + " but got " + root.size()
            );
        }

        Object[] args = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            args[i] = convertJsonNode(root.get(i), parameterTypes[i]);
        }

        return args;
    }

    private Class<?>[] parseParameterTypes(String parameterTypesString) {
        if (parameterTypesString == null || parameterTypesString.trim().isEmpty()) {
            return new Class<?>[0];
        }

        String[] typeNames = parameterTypesString.split("\\s*,\\s*");
        Class<?>[] parameterTypes = new Class<?>[typeNames.length];

        for (int i = 0; i < typeNames.length; i++) {
            parameterTypes[i] = resolveType(typeNames[i]);
        }

        return parameterTypes;
    }

    private Class<?> resolveType(String typeName) {
        return switch (typeName) {
            case "int" -> int.class;
            case "boolean" -> boolean.class;
            case "double" -> double.class;
            case "String" -> String.class;
            case "int[]" -> int[].class;
            case "String[]" -> String[].class;
            default -> throw new IllegalArgumentException("Unsupported parameter type: " + typeName);
        };
    }

    private Object convertJsonNode(JsonNode node, Class<?> targetType) throws IOException {
        if (targetType == int.class || targetType == Integer.class) {
            return node.asInt();
        }

        if (targetType == boolean.class || targetType == Boolean.class) {
            return node.asBoolean();
        }

        if (targetType == double.class || targetType == Double.class) {
            return node.asDouble();
        }

        if (targetType == String.class) {
            return node.asText();
        }

        if (targetType == int[].class) {
            return objectMapper.treeToValue(node, int[].class);
        }

        if (targetType == String[].class) {
            return objectMapper.treeToValue(node, String[].class);
        }

        throw new IllegalArgumentException("Unsupported conversion target type: " + targetType.getName());
    }

    private boolean compareOutput(Object actual, String expectedOutputJson, Class<?> returnType) throws IOException {
        JsonNode expectedNode = objectMapper.readTree(expectedOutputJson);

        if (returnType == int.class || returnType == Integer.class) {
            return actual != null && ((Integer) actual) == expectedNode.asInt();
        }

        if (returnType == boolean.class || returnType == Boolean.class) {
            return actual != null && ((Boolean) actual) == expectedNode.asBoolean();
        }

        if (returnType == double.class || returnType == Double.class) {
            return actual != null && Double.compare((Double) actual, expectedNode.asDouble()) == 0;
        }

        if (returnType == String.class) {
            return actual != null && actual.equals(expectedNode.asText());
        }

        if (returnType == int[].class) {
            int[] expected = objectMapper.treeToValue(expectedNode, int[].class);
            return actual != null && Arrays.equals((int[]) actual, expected);
        }

        if (returnType == String[].class) {
            String[] expected = objectMapper.treeToValue(expectedNode, String[].class);
            return actual != null && Arrays.equals((String[]) actual, expected);
        }

        throw new IllegalArgumentException("Unsupported return type: " + returnType.getName());
    }

    private Method findMatchingMethod(Class<?> solutionClass, String methodName, int argCount) {
        for (Method method : solutionClass.getDeclaredMethods()) {
            if (method.getName().equals(methodName) && method.getParameterCount() == argCount) {
                return method;
            }
        }

        throw new RuntimeException("Method not found: " + methodName + " with " + argCount + " arguments.");
    }

    private String formatActualOutput(Object actual) {
        if (actual == null) {
            return "null";
        }

        if (actual instanceof int[] arr) {
            return Arrays.toString(arr);
        }

        if (actual instanceof String[] arr) {
            return Arrays.toString(arr);
        }

        return actual.toString();
    }
}
