package ru.sharipovar.bookhunter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.service.UserService;

import java.util.Random;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest {

    @Autowired
    private UserService userService;
    private final Random random = new Random();
    private static final long equatorLength = (long) 40e6;
    private static final long amount = 50;

    @Autowired
    private WebTestClient webTestClient;
    private WebTestClient.ResponseSpec responseSpec;
    private User user;

    private void givenUser() {
        var users = userService.getUsers();
        user = users.get(random.nextInt(users.size()));
    }

    private void whenGetNearestByCoordinates(long distance) {
        responseSpec = webTestClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/user/nearest")
                                .queryParam("latitude", user.getLatitude())
                                .queryParam("longitude", user.getLongitude())
                                .queryParam("distance", distance)
                                .build()
                )
                .exchange();
    }

    private void whenGetNearestById(long distance) {
        responseSpec = webTestClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/user/{id}/nearest")
                                .queryParam("distance", distance)
                                .build(user.getId())
                )
                .exchange();
    }

    private void thenResponseShouldContain(long n) {
        responseSpec.expectStatus().is2xxSuccessful().expectBody().jsonPath("$.length()").isEqualTo(n);
    }

    @Test
    public void testGetNearestById() {
        givenUser();
        whenGetNearestById(0);
        thenResponseShouldContain(0);

        givenUser();
        whenGetNearestById(equatorLength);
        thenResponseShouldContain(amount);
    }

    @Test
    public void testGetNearestByCoordinates() {
        givenUser();
        whenGetNearestByCoordinates(0);
        thenResponseShouldContain(1);

        givenUser();
        whenGetNearestByCoordinates(equatorLength);
        thenResponseShouldContain(amount);
    }


}
