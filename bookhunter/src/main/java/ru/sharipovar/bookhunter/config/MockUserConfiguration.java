package ru.sharipovar.bookhunter.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Configuration;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.repository.MockUserRepository;
import ru.sharipovar.bookhunter.service.UserService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Configuration
public class MockUserConfiguration {

    public MockUserRepository userRepository() {
        List<User> users = new LinkedList<>();
        User admin = UserService.constructUser(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                "ivegotsauce",
                "Alexander Sharipov",
                Date.valueOf(LocalDate.of(2002, 8, 9)),
                User.Gender.MALE,
                0,
                0
        );
        users.add(admin);

        Random random = new Random();
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            Date date = new Date(faker.date().between(Date.valueOf(LocalDate.of(1950, 1, 1)),
                    Date.valueOf(LocalDate.of(2003, 1, 1))).getTime());
            User user = UserService.constructUser(
                    UUID.randomUUID(),
                    name.toLowerCase(Locale.ROOT).split(" ")[0] + date.toLocalDate().getYear(),
                    name,
                    date,
                    User.Gender.HIDDEN,
                    random.nextDouble(90) * (random.nextBoolean() ? 1 : -1),
                    random.nextDouble(180) * (random.nextBoolean() ? 1 : -1)
            );
            users.add(user);
        }
        return new MockUserRepository(users);
    }

}
