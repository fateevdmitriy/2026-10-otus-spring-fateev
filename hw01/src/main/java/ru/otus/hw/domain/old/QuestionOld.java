package ru.otus.hw.domain.old;

import java.util.List;

public class QuestionOld {
    private final String questionText;

    private final List<String> answers;

    private final String correctAnswer;

    public QuestionOld(String questionText, String correctAnswer, List<String> answers) {
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