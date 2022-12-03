package ru.sharipovar.bookhunter.repository;

import ru.sharipovar.bookhunter.domain.User;

import java.util.List;

public class MockUserRepository {

    private final List<User> users;



    public MockUserRepository(List<User> users) {
        this.users = users;
    }

    public List<User> findAll() {
        return users;
    }
}
