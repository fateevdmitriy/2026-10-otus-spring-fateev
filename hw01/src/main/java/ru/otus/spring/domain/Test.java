package ru.otus.spring.domain;

import java.util.List;
import java.util.Map;

public class Test {
    private final Person person;

    private final List<Question> questions;

    private final Map<Question, String> results;

    public Test(Person person, List<Question> questions, Map<Question, String> results) {
        this.person = person;
        this.questions = questions;
        this.results = results;
    }

    public Person getPerson() {
        return person;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public Map<Question, String> getResults() {
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