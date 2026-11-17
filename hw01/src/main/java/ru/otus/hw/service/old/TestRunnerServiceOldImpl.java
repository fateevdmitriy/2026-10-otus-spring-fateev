package ru.otus.hw.service.old;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.hw.domain.Person;
import ru.otus.hw.domain.old.QuestionOld;
import ru.otus.hw.domain.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class TestRunnerServiceOldImpl implements TestRunnerServiceOld {

    private String csvFileName;

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public Test run(Person person, Scanner scanner) {
        List<QuestionOld> questions = loadQuestions();
        Map<QuestionOld, String> results = new LinkedHashMap<>();

        for (QuestionOld q : questions) {
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

    private List<QuestionOld> loadQuestions() {
        if (csvFileName == null || csvFileName.isBlank()) {
            throw new RuntimeException("csvFileName is not set");
        }
        List<QuestionOld> questions = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream(csvFileName);
        if (is == null) {
            throw new RuntimeException("CSV not found: " + csvFileName);
        }
        try (CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            List<String[]> allRows = reader.readAll();
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                if (row.length >= allRows.get(0).length) {
                    questions.add(new QuestionOld(row[0].trim(), row[5].trim(),
                            List.of(row[1].trim(), row[2].trim(), row[3].trim(), row[4].trim())));
                }
            }
            is.close();
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return questions;
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