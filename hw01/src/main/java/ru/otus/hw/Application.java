package ru.otus.hw;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.domain.old.Person;
import ru.otus.hw.domain.old.QuestionOld;
import ru.otus.hw.domain.old.Test;
import ru.otus.hw.service.TestRunnerService;
import ru.otus.hw.service.old.PersonService;
import ru.otus.hw.service.old.TestRunnerServiceOld;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        //PersonService service = context.getBean(PersonService.class);
        TestRunnerService testRunner = context.getBean(TestRunnerService.class);

        /*
        Person person = service.getByName(getNameFromUser(scanner));
        System.out.println("\nHello, " + person.getName() + "!\nStarting the test:\n");
        */
        testRunner.run();

        /*
        System.out.println(System.lineSeparator() + "Results:");
        for (QuestionOld q : test.getQuestions()) {
            String userAnswer = test.getResults().get(q);
            boolean isCorrect = q.getCorrectAnswer().equalsIgnoreCase(userAnswer);
            System.out.printf("Question: %s - %s%n", isCorrect ? "Correct" : "Incorrect", q.getQuestionText());
        }
        System.out.printf("%nTotal: correct answers %d out of %d%n", test.getCorrectCount(), test.getQuestions().size());
        scanner.close();
         */
    }

    private static String getNameFromUser(Scanner scanner) {
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
