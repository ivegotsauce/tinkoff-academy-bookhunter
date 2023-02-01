package ru.sharipovar.bookhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.sharipovar.bookhunter.domain.User;

import java.sql.Date;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Modifying
    @Query("update User u set u.nick = ?2, u.name = ?3, u.dateOfBirth=?4, u.gender=?5, u.latitude=?6, u.longitude=?7 where u.id = ?1")
    void setUserInfoById(UUID id, String nick, String name, Date dateOfBirth, User.Gender gender, double latitude,
                         double longitude);
}
