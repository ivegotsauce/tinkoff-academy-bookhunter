package ru.sharipovar.bookhunter;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sharipovar.bookhunter.domain.User;

import java.util.*;

@SpringBootApplication
public class BookHunterApplication {

    public static List<User> users = new LinkedList<>();

	public static void main(String[] args) {
        Random random = new Random();
        Faker faker = new Faker();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setAge(18 + random.nextLong(30));
            user.setGender(User.Gender.HIDDEN);
            user.setLatitude(random.nextDouble(90) * (random.nextBoolean() ? 1 : -1));
            user.setLongitude(random.nextDouble(180) * (random.nextBoolean() ? 1 : -1));
            user.setName(faker.name().fullName());
            user.setNick(user.getName().toLowerCase(Locale.ROOT).split(" ")[0] + (2022 - user.getAge()));
            user.setId(UUID.randomUUID());
            System.out.println("{");
            System.out.println("\tnick: " + user.getNick());
            System.out.println("\tname: " + user.getName());
            System.out.println("\tgender: " + user.getGender().toString());
            System.out.println("\tlocation: " + user.getLatitude() + " " + user.getLongitude());
            System.out.println("\tage: " + user.getAge());
            System.out.println("\tid: " + user.getId());
            System.out.println("}");
            users.add(user);
        }
        SpringApplication.run(BookHunterApplication.class, args);
	}

}
