package ru.otus.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.PersonService;
import ru.otus.spring.service.TestReaderService;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        // создание класс контекста
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        // получение TestReaderService
        TestReaderService testReader = context.getBean(TestReaderService.class);
        List<Question> questions = testReader.loadQuestions();
        for (Question q : questions) {
            System.out.println(q.getQuestionText());
            for (int i = 0; i < q.getOptions().size(); i++) {
                System.out.println((i + 1) + ". " + q.getOptions().get(i));
            }
            System.out.println("Correct: " + q.getCorrectAnswer() + "\n");
        }

        // получение Person Service
        PersonService service = context.getBean(PersonService.class);
        // получение Person "Ivan"
        Person ivan = service.getByName("Ivan");

        System.out.println("name: " + ivan.getName() + " age: " + ivan.getAge());
    }
}
