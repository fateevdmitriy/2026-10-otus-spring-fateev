package ru.otus.hw.dao.old;

import ru.otus.hw.domain.old.Person;

public interface PersonDao {

    Person findByName(String name);

}
