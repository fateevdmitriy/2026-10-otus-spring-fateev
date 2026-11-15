package ru.otus.spring;

//import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Person;
import ru.otus.spring.service.PersonService;

public class Main {

    public static void main(String[] args) {
        // создание класс контекста
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        // получение Person Service
        PersonService service = context.getBean(PersonService.class);
        // получение Person "Ivan"
        Person ivan = service.getByName("Ivan");

        System.out.println("name: " + ivan.getName() + " age: " + ivan.getAge());
    }
}
