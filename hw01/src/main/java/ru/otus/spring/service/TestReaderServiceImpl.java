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

    private String csvFileName;

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public List<Question> loadQuestions() {
        if (csvFileName == null || csvFileName.isBlank()) {
            throw new RuntimeException("csvFileName is not set");
        }
        List<Question> questions = new ArrayList<>();
        try (InputStream is = getClass().getResourceAsStream(csvFileName);
             CSVReader reader = new CSVReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            List<String[]> allRows = reader.readAll();
            if (allRows.isEmpty()) {
                return questions;
            }
            for (int i = 1; i < allRows.size(); i++) {
                String[] row = allRows.get(i);
                if (row.length >= allRows.get(0).length) {
                    questions.add(new Question(row[0].trim(), row[5].trim(),
                            List.of(row[1].trim(), row[2].trim(), row[3].trim(), row[4].trim())));
                }
            }
        } catch (IOException | CsvException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
        return questions;
    }
}
