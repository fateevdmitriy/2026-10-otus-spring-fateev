package ru.otus.hw.service.old;

import ru.otus.hw.domain.Person;
import ru.otus.hw.domain.Test;

import java.util.Scanner;

public interface TestRunnerServiceOld {

    Test run(Person person, Scanner scanner);
}