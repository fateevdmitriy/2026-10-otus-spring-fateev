package ru.otus.hw;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.hw.service.TestRunnerService;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestRunnerService testRunner = context.getBean(TestRunnerService.class);
        /*
        PersonService service = context.getBean(PersonService.class);

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
}
