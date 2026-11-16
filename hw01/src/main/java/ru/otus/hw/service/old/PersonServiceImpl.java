package ru.otus.hw.service.old;

import ru.otus.hw.dao.old.PersonDao;
import ru.otus.hw.domain.old.Person;

public class PersonServiceImpl implements PersonService {

    private final PersonDao dao;

    public PersonServiceImpl(PersonDao dao) {
        this.dao = dao;
    }

    public Person getByName(String name) {
        return dao.findByName(name);
    }

}
