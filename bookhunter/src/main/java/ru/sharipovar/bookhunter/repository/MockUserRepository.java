package ru.sharipovar.bookhunter.repository;

import ru.sharipovar.bookhunter.domain.User;

import java.util.List;

public class MockUserRepository implements UserRepository{

    private final List<User> users;



    public MockUserRepository(List<User> users) {
        this.users = users;
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}
