package ru.otus.hw.domain.old;

import java.util.List;
import java.util.Map;

public class Test {
    private final Person person;

    private final List<QuestionOld> questions;

    private final Map<QuestionOld, String> results;

    public Test(Person person, List<QuestionOld> questions, Map<QuestionOld, String> results) {
        this.person = person;
        this.questions = questions;
        this.results = results;
    }

    public Person getPerson() {
        return person;
    }

    public List<QuestionOld> getQuestions() {
        return questions;
    }

    public Map<QuestionOld, String> getResults() {
        return results;
    }

    public int getCorrectCount() {
        return (int) results.entrySet().stream()
                .filter(e -> e.getValue() != null && e.getValue().equalsIgnoreCase(e.getKey().getCorrectAnswer()))
                .count();
    }

    @Override
    public String toString() {
        return "Test{" +
                "person=" + person.getName() +
                ", correct=" + getCorrectCount() +
                ", total=" + questions.size() +
                '}';
    }
}