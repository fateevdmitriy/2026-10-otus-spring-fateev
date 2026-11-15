package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TestReaderServiceImplTest {

    private TestReaderServiceImpl testReaderService;

    @BeforeEach
    void setUp() {
        testReaderService = new TestReaderServiceImpl();
    }

    @Test
    void loadQuestions_shouldReturnAllQuestions() {
        testReaderService.setCsvFileName("/java_basic_test.csv");
        List<Question> questions = testReaderService.loadQuestions();

        assertThat(questions).isNotEmpty();
        assertThat(questions).hasSize(10);
    }

    @Test
    void loadQuestions_shouldSetCorrectQuestionText() {
        testReaderService.setCsvFileName("/java_basic_test.csv");
        List<Question> questions = testReaderService.loadQuestions();

        Question firstQuestion = questions.get(0);
        assertThat(firstQuestion.getQuestionText()).isEqualTo("What does the abbreviation JVM stand for?");
    }

    @Test
    void loadQuestions_shouldSetCorrectAnswers() {
        testReaderService.setCsvFileName("/java_basic_test.csv");
        List<Question> questions = testReaderService.loadQuestions();

        Question firstQuestion = questions.get(0);
        assertThat(firstQuestion.getAnswers()).hasSize(4);
        assertThat(firstQuestion.getAnswers()).contains(
                "Java Verified Machine",
                "Java Variable Manager",
                "Java Virtual Machine",
                "Joint Visual Module"
        );
    }

    @Test
    void loadQuestions_shouldSetCorrectAnswer() {
        testReaderService.setCsvFileName("/java_basic_test.csv");
        List<Question> questions = testReaderService.loadQuestions();

        Question firstQuestion = questions.get(0);
        assertThat(firstQuestion.getCorrectAnswer()).isEqualTo("Java Virtual Machine");
    }

    @Test
    void loadQuestions_shouldThrowExceptionWhenCsvFileNameIsNull() {
        assertThatThrownBy(() -> testReaderService.loadQuestions())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("csvFileName is not set");
    }

    @Test
    void loadQuestions_shouldThrowExceptionWhenCsvFileNotFound() {
        testReaderService.setCsvFileName("/nonexistent.csv");
        assertThatThrownBy(() -> testReaderService.loadQuestions())
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("CSV not found");
    }
}
