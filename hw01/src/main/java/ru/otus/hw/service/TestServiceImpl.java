package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        String fullName = getNameFromUser();
        ioService.printLine("");
        ioService.printFormattedLine("Hello, %s!", fullName);
        ioService.printFormattedLine("Please answer the questions below:%n");
        // Получить вопросы из дао и вывести их с вариантами ответов
        List<Question> questions = questionDao.findAll();
        for (Question q : questions) {
            if (q.answers() == null || q.answers().isEmpty()) {
                ioService.printLine("Q: " + q.text() + ", Answers: " + q.answers());
                continue;
            }
            ioService.printFormattedLine("Question: %s%n", q.text());
            for (int i = 0; i < q.answers().size(); i++) {
                Answer answer = q.answers().get(i);
                ioService.printFormattedLine("%d. %s%n", i + 1, answer.text());
            }
            ioService.printLine("");
        }
    }

    private String getNameFromUser() {
        String lastName;
        while (true) {
            lastName = ioService.readLine("Enter last name: ");
            if (!lastName.isEmpty()) {
                break;
            }
            System.out.println("Last name cannot be empty. Try again.");
        }
        while (true) {
            String firstName = ioService.readLine("Enter first name: ");
            if (!firstName.isEmpty()) {
                return lastName + " " + firstName;
            }
            System.out.println("First name cannot be empty. Try again.");
        }
    }

}
