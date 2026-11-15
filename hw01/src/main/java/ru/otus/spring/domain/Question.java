package ru.otus.spring.domain;

import java.util.List;

public class Question {
    private final String questionText;

    private final List<String> answers;

    private final String correctAnswer;

    public Question(String questionText, String correctAnswer, List<String> answers) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}