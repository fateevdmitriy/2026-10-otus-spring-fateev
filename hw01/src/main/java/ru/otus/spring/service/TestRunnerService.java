package ru.otus.spring.service;

import ru.otus.spring.domain.Person;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Test;

import java.util.List;
import java.util.Scanner;

public interface TestRunnerService {

    Test run(Person person, List<Question> questions, Scanner scanner);
}