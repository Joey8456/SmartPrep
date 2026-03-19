package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a single questionnaire question shown to new users on signup.
 * Each question is tied to a Category and awards points toward that category's
 * proficiency score when answered correctly.
 */
@Entity
@Table(name = "Questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "question_ID", updatable = false, nullable = false)
    private UUID questionId;

    @Column(name = "question_text", nullable = false, length = 1000)
    private String questionText;

    @Column(name = "option_a", nullable = false)
    private String optionA;

    @Column(name = "option_b", nullable = false)
    private String optionB;

    @Column(name = "option_c")
    private String optionC;

    @Column(name = "option_d")
    private String optionD;

    /**
     * The correct answer key: "A", "B", "C", or "D"
     */
    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    /**
     * How many proficiency points a correct answer awards for this question's category.
     * Allows harder questions to be worth more points.
     */
    @Column(name = "point_value", nullable = false)
    private int pointValue;

    @ManyToOne
    @JoinColumn(name = "category_ID", nullable = false)
    private Category category;

    public Question() {}

    public Question(String questionText, String optionA, String optionB, String optionC,
                    String optionD, String correctAnswer, int pointValue, Category category) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.pointValue = pointValue;
        this.category = category;
    }

    // --- Getters & Setters ---

    public UUID getQuestionId() { return questionId; }
    public void setQuestionId(UUID questionId) { this.questionId = questionId; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }

    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }

    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }

    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public int getPointValue() { return pointValue; }
    public void setPointValue(int pointValue) { this.pointValue = pointValue; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(questionId, question.questionId);
    }

    @Override
    public int hashCode() { return Objects.hashCode(questionId); }

    @Override
    public String toString() {
        return "Question{questionId=" + questionId + ", questionText='" + questionText + "', category=" + category + '}';
    }
}
