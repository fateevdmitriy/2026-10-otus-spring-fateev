package ru.otus.hw.dao.old;

import ru.otus.hw.domain.old.Person;

public class PersonDaoSimple implements PersonDao {

    private int defaultAge;

    public void setDefaultAge(int defaultAge) {
        this.defaultAge = defaultAge;
    }

    public Person findByName(String name) {
        return new Person(name, defaultAge);
    }

}
