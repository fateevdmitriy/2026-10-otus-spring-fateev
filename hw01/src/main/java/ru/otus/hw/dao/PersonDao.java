package ru.otus.hw.dao;

import ru.otus.hw.domain.Person;

public interface PersonDao {

    Person findByName(String name);

}
