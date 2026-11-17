package ru.otus.hw.service;

import ru.otus.hw.dao.PersonDao;
import ru.otus.hw.domain.Person;

public class PersonServiceImpl implements PersonService {

    private final PersonDao dao;

    public PersonServiceImpl(PersonDao dao) {
        this.dao = dao;
    }

    public Person getByName(String name) {
        return dao.findByName(name);
    }

}
