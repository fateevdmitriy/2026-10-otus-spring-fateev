package ru.otus.spring.service;

import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TestRunnerServiceImpl implements TestRunnerService {

    @Override
    public Test run(Person person, List<Question> questions, Scanner scanner) {
        Map<Question, String> results = new LinkedHashMap<>();

        for (Question q : questions) {
            System.out.println(q.getQuestionText());
            for (int i = 0; i < q.getAnswers().size(); i++) {
                System.out.println((i + 1) + ". " + q.getAnswers().get(i));
            }
            System.out.print("Your answer (1-" + q.getAnswers().size() + "): ");
            String input = scanner.nextLine().trim();
            System.out.println();
            int index = parseAnswerIndex(input, q.getAnswers().size());
            String answer = index > 0 ? q.getAnswers().get(index - 1) : "";
            results.put(q, answer);
        }
        return new Test(person, questions, results);
    }

    private int parseAnswerIndex(String input, int maxIndex) {
        try {
            int index = Integer.parseInt(input);
            return (index >= 1 && index <= maxIndex) ? index : -1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number.");
        }
        return -1;
    }
}