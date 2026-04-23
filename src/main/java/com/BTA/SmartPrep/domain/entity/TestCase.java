package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Test_Cases")
public class TestCase {
    public TestCase() {
    }

    public TestCase(String testId, int problemId, String input_args, String expectedOutput, int isHidden) {
        this.testId = testId;
        this.problemId = problemId;
        this.input_args =input_args;
        this.expectedOutput = expectedOutput;
        this.is_hidden = isHidden;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "testId",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private String testId;

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
        this.problemId = problemId;
    }

    @Column(name = "problem_ID")
    private int problemId;

    public String getInput_args() {
        return input_args;
    }

    public void setInput_args(String input_args) {
        this.input_args = input_args;
    }

    @Column(name = "input_args")
    private String input_args;

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    @Column(name = "expected_output")
    private String expectedOutput;

    public int getIs_hidden() {
        return is_hidden;
    }

    public void setIs_hidden(int is_hidden) {
        this.is_hidden = is_hidden;
    }

    @Column(name = "is_hidden")
    private int is_hidden;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TestCase testCase = (TestCase) o;
        return Objects.equals(testId, testCase.testId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(testId);
    }
}
