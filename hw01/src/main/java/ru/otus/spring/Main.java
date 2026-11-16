package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.PersonService;
import ru.otus.spring.service.TestReaderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        PersonService service = context.getBean(PersonService.class);
        Person person = service.getByName(getNameFormUser(scanner));
        System.out.println("\nHello, " + person.getName() + "!\nStarting the test:\n");
        TestReaderService testReader = context.getBean(TestReaderService.class);
        List<Question> questions = testReader.loadQuestions();
        List<String> answers = runTest(questions, scanner);
        System.out.println("Results:");
        int correctCount = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            String userAnswer = answers.get(i);
            boolean isCorrect = q.getCorrectAnswer().equalsIgnoreCase(userAnswer);
            if (isCorrect) {
                correctCount++;
            }
            System.out.printf("Question %d: %s -> %s%n", i + 1,
                    isCorrect ? "Correct" : "Incorrect",
                    q.getQuestionText());
        }
        System.out.printf("%nTotal: correct answers %d out of %d%n", correctCount, questions.size());
        scanner.close();
    }

    private static List<String> runTest(List<Question> questions, Scanner scanner) {
        List<String> answers = new ArrayList<>();
        for (Question q : questions) {
            // print question text
            System.out.println(q.getQuestionText());
            for (int i = 0; i < q.getAnswers().size(); i++) {
                // print answer options
                System.out.println((i + 1) + ". " + q.getAnswers().get(i));
            }
            // prompt for answer number
            System.out.print("Your answer (1-" + q.getAnswers().size() + "): ");
            String input = scanner.nextLine().trim();
            int index = parseAnswerIndex(input, q.getAnswers().size());
            if (index >= 1 && index <= q.getAnswers().size()) {
                answers.add(q.getAnswers().get(index - 1));
            } else {
                answers.add("");
            }
            System.out.println();
        }
        return answers;
    }

    private static int parseAnswerIndex(String input, int maxIndex) {
        try {
            int index = Integer.parseInt(input);
            if (index >= 1 && index <= maxIndex) {
                return index;
            }
            System.out.println("Enter a number from 1 to " + maxIndex + ".");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Enter a number.");
        }
        return -1;
    }


    private static String getNameFormUser(Scanner scanner) {
        String lastName;
        while (true) {
            System.out.print("Enter last name: ");
            lastName = scanner.nextLine().trim();
            if (!lastName.isEmpty()) {
                break;
            }
            System.out.println("Last name cannot be empty. Try again.");
        }
        while (true) {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine().trim();
            if (!firstName.isEmpty()) {
                return lastName + " " + firstName;
            }
            System.out.println("First name cannot be empty. Try again.");
        }
    }
}
