package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Person;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final PersonService personService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        String fullName = getNameFromUser();
        Person person = personService.getByName(fullName);

        ioService.printLine("");
        ioService.printFormattedLine("Hello, %s!", person.getName());
        ioService.printFormattedLine("Please answer the questions below:%n");

        List<Question> questions = questionDao.findAll();
        List<Answer> userAnswers = new ArrayList<>();

        for (Question q : questions) {
            userAnswers.add(getUsersAnswer(q));
        }
        printTestResults(questions, userAnswers);
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

    private Answer getUsersAnswer(Question q) {
        if (q.text() == null || q.text().isEmpty() || q.answers() == null || q.answers().isEmpty()) {
            throw new QuestionReadException("Question text or answer options not specified.");
        }
        int maxAnswerNumber = q.answers().size();
        ioService.printFormattedLine("Question: %s", q.text());
        for (int i = 0; i < maxAnswerNumber; i++) {
            ioService.printFormattedLine("%d. %s", i + 1, q.answers().get(i).text());
        }
        while (true) {
            String input = ioService.readLine("Your answer (1-" + maxAnswerNumber + "): ");
            try {
                int index = Integer.parseInt(input);
                if (index >= 1 && index <= maxAnswerNumber) {
                    ioService.printLine("");
                    return q.answers().get(index - 1);
                }
                ioService.printFormattedLine("Invalid input. Enter a number 1-%d.", maxAnswerNumber);
            } catch (NumberFormatException e) {
                ioService.printFormattedLine("Invalid input. Enter a number 1-%d.", maxAnswerNumber);
            }
        }
    }

    private void printTestResults(List<Question> questions, List<Answer> userAnswers) {
        ioService.printLine("Test results:");
        int correctAnswers = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            Answer userAnswer = userAnswers.get(i);
            Answer correctAnswer = q.answers().stream()
                    .filter(Answer::isCorrect)
                    .findFirst()
                    .orElse(null);
            boolean isCorrect = userAnswer.equals(correctAnswer);
            if (isCorrect) {
                correctAnswers++;
            }
            ioService.printLine((i + 1) + ". " + userAnswer.text() + ", isCorrect: " + isCorrect);
        }
        ioService.printFormattedLine("Total: %d of %d correct answers", correctAnswers, questions.size());
    }
}
