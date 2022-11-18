package ru.sharipovar.bookhunter.config;

import com.github.javafaker.Faker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.repository.MockUserRepository;
import ru.sharipovar.bookhunter.repository.UserRepository;
import ru.sharipovar.bookhunter.service.UserService;

import java.time.Year;
import java.util.*;

@Configuration
public class MockUserConfiguration {

    @Bean
    public UserRepository userRepository() {
        List<User> users = new LinkedList<>();
        long year = Year.now().getValue();

        User admin = UserService.constructUser(
                UUID.fromString("00000000-0000-0000-0000-000000000000"),
                "ivegotsauce",
                "Alexander Sharipov",
                year - 2002L,
                User.Gender.MALE,
                0,
                0
        );
        users.add(admin);

        Random random = new Random();
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            String name = faker.name().fullName();
            long age = 18 + random.nextLong(30);
            User user = UserService.constructUser(
                    UUID.randomUUID(),
                    name.toLowerCase(Locale.ROOT).split(" ")[0] + (year - age),
                    name,
                    age,
                    User.Gender.HIDDEN,
                    random.nextDouble(90) * (random.nextBoolean() ? 1 : -1),
                    random.nextDouble(180) * (random.nextBoolean() ? 1 : -1)
            );
            users.add(user);
        }
        return new MockUserRepository(users);
    }

}
