package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name="Problems")
public class Problem {
    public Problem() {
    }

    public Problem(UUID problemId, String title, String prompt, String examples, ProblemDifficulty problemDifficulty, Category category) {
        this.problemId = problemId;
        this.title = title;
        this.prompt = prompt;
        this.examples = examples;
        this.problemDifficulty = problemDifficulty;
        this.category = category;
    }

    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "problem_ID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private UUID problemId;

    @Column(name = "pTitle")
    private String title;

    @Column(name = "prompt")
    private String prompt;

    @Column(name = "examples")
    private String examples;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private ProblemDifficulty problemDifficulty;

    @ManyToOne
    @JoinColumn(name = "category_ID")
    private Category category;

    public UUID getProblemId() {
        return problemId;
    }

    public void setProblemId(UUID problemId) {
        this.problemId = problemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getExamples() {
        return examples;
    }

    public void setExamples(String examples) {
        this.examples = examples;
    }

    public ProblemDifficulty getProblemDifficulty() {
        return problemDifficulty;
    }

    public void setProblemDifficulty(ProblemDifficulty problemDifficulty) {
        this.problemDifficulty = problemDifficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return Objects.equals(problemId, problem.problemId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(problemId);
    }

    @Override
    public String toString() {
        return "Problem{" +
                "problemId=" + problemId +
                ", title='" + title + '\'' +
                ", prompt='" + prompt + '\'' +
                ", examples='" + examples + '\'' +
                ", problemDifficulty=" + problemDifficulty +
                ", category=" + category +
                '}';
    }
}
