package ru.otus.hw.service;

import ru.otus.hw.domain.Person;

public interface PersonService {

    Person getByName(String name);
}
