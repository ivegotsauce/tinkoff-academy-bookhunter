package ru.sharipovar.bookhunter;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.sharipovar.bookhunter.domain.User;

import java.util.*;

@SpringBootApplication
public class BookHunterApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookHunterApplication.class, args);
	}

}
