package ru.sharipovar.bookhunter.repository;

import ru.sharipovar.bookhunter.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}
