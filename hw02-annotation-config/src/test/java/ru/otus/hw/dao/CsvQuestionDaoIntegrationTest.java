package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CsvQuestionDaoIntegrationTest {

    @Test
    void findAll_shouldLoadQuestionsFromCsv() {
        TestFileNameProvider properties = mock(TestFileNameProvider.class);
        when(properties.getTestFileName()).thenReturn("/test-questions.csv");

        CsvQuestionDao dao = new CsvQuestionDao(properties);
        List<Question> questions = dao.findAll();

        assertEquals(3, questions.size());
        assertEquals("Which keyword is used to declare a constant in Java?", questions.get(0).text());
        assertTrue(questions.get(0).answers().get(0).isCorrect());
        assertFalse(questions.get(0).answers().get(1).isCorrect());
        assertFalse(questions.get(0).answers().get(2).isCorrect());
    }

    @Test
    void findAll_shouldThrowOnMissingFile() {
        TestFileNameProvider properties = mock(TestFileNameProvider.class);
        when(properties.getTestFileName()).thenReturn("/non-existent.csv");

        CsvQuestionDao dao = new CsvQuestionDao(properties);

        QuestionReadException exception = assertThrows(QuestionReadException.class, dao::findAll,
                "Should throw QuestionReadException for missing file");
        assertTrue(exception.getMessage().contains("CSV file not found"),
                "Exception message should contain 'CSV file not found'");
    }
}
