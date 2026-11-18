
package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Person;
import ru.otus.hw.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestServiceImplTest {

    private TestServiceImpl testService;

    private List<String> outputLines;

    private List<String> inputLines;

    @BeforeEach
    void setUp() {
        // Создаём тестовые вопросы
        List<Question> questions = List.of(
                new Question("What is 2 + 2 ?",
                        List.of(
                                new Answer("3", false),
                                new Answer("4", true),
                                new Answer("5", false)
                        )
                )
        );

        // Заготавливаем входные данные (имя, фамилия, номер ответа)
        inputLines = new ArrayList<>(List.of("Ivan", "Petrov", "2"));

        // Создаём "поддельный" IO-сервис, который собирает вывод
        outputLines = new ArrayList<>();
        IOService ioService = new IOService() {
            @Override
            public void printLine(String s) {
                outputLines.add(s);
            }

            @Override
            public void printFormattedLine(String s, Object... args) {
                outputLines.add(String.format(s, args));
            }

            @Override
            public String readLine(String prompt) {
                // Возвращаем по одному значению из inputLines
                return inputLines.remove(0);
            }
        };

        // Создаём "поддельный" DAO, который возвращает тестовые вопросы
        QuestionDao questionDao = new QuestionDao() {
            @Override
            public List<Question> findAll() {
                return questions;
            }
        };

        // Создаём "поддельный" сервис для поиска персон
        PersonService personService = new PersonService() {
            @Override
            public Person getByName(String name) {
                return new Person(name, 25);
            }
        };

        // Создаём тестируемый сервис с подменёнными зависимостями
        testService = new TestServiceImpl(ioService, personService, questionDao);
    }

    @Test
    void executeTest_shouldDisplayCorrectAnswer() {
        // запускаем тест
        testService.executeTest();

        // Assert: проверяем, что вывелся правильный ответ
        // outputLines.size() - 2 — это строка с результатом ответа
        // (последняя строка — итог "Total: ...", предпоследняя — результат)
        String expectedOutput = "1. 4, isCorrect: true";
        String actualOutput = outputLines.get(outputLines.size() - 2);

        assertEquals(expectedOutput, actualOutput,
                "Должен быть показан правильный ответ");
    }
}