package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "Test_Cases")
public class TestCase {
    public TestCase() {
    }

    public TestCase(String testId, String testCase, String expectedOutput, Problem problem) {
        this.testId = testId;
        this.testCase = testCase;
        this.expectedOutput = expectedOutput;
        this.problem = problem;
    }

    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "submission_ID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private String testId;

    @Column(name = "testCase")
    private String testCase;

    @Column(name = "expected_Out")
    private String expectedOutput;

    @ManyToOne
    @JoinColumn(name = "problem_ID")
    private Problem problem;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestCase() {
        return testCase;
    }

    public void setTestCase(String testCase) {
        this.testCase = testCase;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public Problem getProblem() {
        return problem;
    }

    public void setProblem(Problem problem) {
        this.problem = problem;
    }

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

    @Override
    public String toString() {
        return "TestCase{" +
                "testId=" + testId +
                ", testCase='" + testCase + '\'' +
                ", expectedOutput='" + expectedOutput + '\'' +
                ", problem=" + problem +
                '}';
    }
}
