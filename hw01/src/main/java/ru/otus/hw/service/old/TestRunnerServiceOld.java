package ru.otus.hw.service.old;

import ru.otus.hw.domain.old.Person;
import ru.otus.hw.domain.old.Test;

import java.util.Scanner;

public interface TestRunnerServiceOld {

    Test run(Person person, Scanner scanner);
}