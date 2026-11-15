package ru.otus.spring.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TestReaderServiceImpl implements TestReaderService {

    private static final String CSV_FILE = "/java_basic_test.csv";

    private static final int CSM_MAX_COLUMNS = 6;

    public List<Question> loadQuestions() {
        List<Question> questions = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream(CSV_FILE);
             CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            if (is == null) {
                throw new RuntimeException("CSV file not found: " + CSV_FILE);
            }
            List<String[]> allRows = reader.readAll();
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                if (row.length >= CSM_MAX_COLUMNS) {
                    List<String> answers = List.of(row[1], row[2], row[3], row[4]);
                    String correctAnswerLabel = row[5].trim().toLowerCase();
                    String correctAnswer = getAnswerByLabel(answers, correctAnswerLabel);
                    String question = row[0];
                    questions.add(new Question(question, answers, correctAnswer));
                }
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return questions;
    }

    private String getAnswerByLabel(List<String> options, String label) {
        int index = switch (label) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new IllegalArgumentException("Unknown answer label: " + label);
        };
        return options.get(index);
    }
}
