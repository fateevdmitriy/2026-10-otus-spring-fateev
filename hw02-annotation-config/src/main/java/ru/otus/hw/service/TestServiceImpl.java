package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import ru.otus.hw.exceptions.QuestionReadException;

@RequiredArgsConstructor
@Service
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false; // Задать вопрос, получить ответ
            isAnswerValid = getUsersAnswer(question).isCorrect();
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private Answer getUsersAnswer(Question q) {
        if (q.text() == null || q.text().isEmpty() || q.answers() == null || q.answers().isEmpty()) {
            throw new QuestionReadException("Question text or answer options not specified.");
        }
        int answerMaxNumber = q.answers().size();
        // Вывод текста вопроса
        ioService.printFormattedLine("Question: %s", q.text());
        // Вывод возможных ответов на вопрос
        for (int i = 0; i < answerMaxNumber; i++) {
            ioService.printFormattedLine("%d. %s", i + 1, q.answers().get(i).text());
        }
        // Получение ввода с ответом пользователя
        String answerPrompt = String.format("Your answer (1-%d):", answerMaxNumber);
        String answerErrorMsg = String.format("Invalid input. Enter a number 1-%d.", answerMaxNumber);
        int answeredNumber =  ioService.readIntForRangeWithPrompt(1, answerMaxNumber, answerPrompt, answerErrorMsg);
        return q.answers().get(answeredNumber - 1);
    }

}
