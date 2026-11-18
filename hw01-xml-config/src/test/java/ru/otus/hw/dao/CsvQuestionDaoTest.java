package ru.otus.hw.dao;

import org.junit.jupiter.api.Test;
import ru.otus.hw.config.AppProperties;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvQuestionDaoTest {

    @Test
    void findAll_shouldLoadQuestionsFromCsv() {
        // Подготовка
        AppProperties properties = new AppProperties("/test-questions.csv");
        CsvQuestionDao dao = new CsvQuestionDao(properties);

        // Действие
        List<Question> questions = dao.findAll();

        // Проверка
        assertEquals(3, questions.size(), "Должно быть загружено 3 вопроса");

        // Проверка первого вопроса
        Question firstQuestion = questions.get(0);
        assertEquals("Which keyword is used to declare a constant in Java?", firstQuestion.text());
        assertEquals(3, firstQuestion.answers().size());
        assertTrue(firstQuestion.answers().get(0).isCorrect());
        assertEquals("final", firstQuestion.answers().get(0).text());
        assertFalse(firstQuestion.answers().get(1).isCorrect());
        assertEquals("static", firstQuestion.answers().get(1).text());

        // Проверка второго вопроса
        Question secondQuestion = questions.get(1);
        assertEquals("Which keyword defines an interface in Java?", secondQuestion.text());
        assertEquals(3, secondQuestion.answers().size());
        assertTrue(secondQuestion.answers().get(0).isCorrect());
        assertEquals("interface", secondQuestion.answers().get(0).text());

        // Проверка третьего вопроса
        Question thirdQuestion = questions.get(2);
        assertEquals("What does JVM stand for?", thirdQuestion.text());
        assertEquals(3, thirdQuestion.answers().size());
        assertTrue(thirdQuestion.answers().get(0).isCorrect());
        assertEquals("Java Virtual Machine", thirdQuestion.answers().get(0).text());
    }

    @Test
    void findAll_shouldThrowOnMissingFile() {
        // Подготовка
        AppProperties properties = new AppProperties("/non-existent.csv");
        CsvQuestionDao dao = new CsvQuestionDao(properties);

        // Проверки
        QuestionReadException exception = assertThrows(QuestionReadException.class, dao::findAll,
                "Should throw QuestionReadException for missing file");
        assertTrue(exception.getMessage().contains("CSV file not found"),
                "Exception message should contain 'CSV file not found'");
    }
}
