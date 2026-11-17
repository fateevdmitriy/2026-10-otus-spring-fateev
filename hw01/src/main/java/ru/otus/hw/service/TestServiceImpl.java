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
                continue;
            }
            int usersAnswerIndex = getUserAnswerIndex(q);
            // Сохранить ответ в результаты теста
            //
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
            ioService.printLine("Last name cannot be empty. Try again.");
        }
        while (true) {
            String firstName = ioService.readLine("Enter first name: ");
            if (!firstName.isEmpty()) {
                return lastName + " " + firstName;
            }
            ioService.printLine("First name cannot be empty. Try again.");
        }
    }

    private int getUserAnswerIndex(Question q) {
        int maxAnswerIndex = q.answers().size();
        ioService.printFormattedLine("Question: %s", q.text());
        for (int i = 0; i < maxAnswerIndex; i++) {
            Answer answer = q.answers().get(i);
            ioService.printFormattedLine("%d. %s", i + 1, answer.text());
        }
        while (true) {
            String input = ioService.readLine("Your answer (1-" + maxAnswerIndex + "): ");
            try {
                int index = Integer.parseInt(input);
                if (index >= 1 && index <= maxAnswerIndex) {
                    return index;
                }
                ioService.printFormattedLine("Invalid input. Enter a number 1-%d.", maxAnswerIndex);
            } catch (NumberFormatException e) {
                ioService.printFormattedLine("Invalid input. Enter a number 1-%d.", maxAnswerIndex);
            }
        }
    }

}
