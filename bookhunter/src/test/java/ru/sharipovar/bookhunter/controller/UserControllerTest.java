package ru.sharipovar.bookhunter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.service.UserService;

import java.util.Random;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest extends BaseControllerTest {

    @Autowired
    private UserService userService;
    private final Random random = new Random();
    private static final long equatorLength = (long) 40e6;
    private User user;

    private void givenUser() {
        var users = userService.findAll();
        user = users.get(random.nextInt(users.size()));
    }

    private void whenGetNearestByCoordinates() {
        responseSpec = webTestClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/user/nearest")
                                .queryParam("latitude", user.getLatitude())
                                .queryParam("longitude", user.getLongitude())
                                .queryParam("distance", equatorLength)
                                .build()
                )
                .exchange();
    }

    private void whenGetNearestById() {
        responseSpec = webTestClient.get().uri(
                        uriBuilder -> uriBuilder
                                .path("/user/{id}/nearest")
                                .queryParam("distance", equatorLength)
                                .build(user.getId())
                )
                .exchange();
    }

    private void thenResponseShouldContainJson() {
        responseSpec.expectAll(
                rSpec -> rSpec.expectStatus().is2xxSuccessful(),
                rSpec -> rSpec.expectBody().jsonPath("$[0].nick").exists()
                        .jsonPath("$[0].name").exists()
                        .jsonPath("$[0].age").exists()
                        .jsonPath("$[0].gender").exists()
                        .jsonPath("$[0].location").exists()
        );

    }

    @Test
    public void testGetNearestById() {
        givenUser();
        whenGetNearestById();
        thenResponseShouldContainJson();
    }

    @Test
    public void testGetNearestByCoordinates() {
        givenUser();
        whenGetNearestByCoordinates();
        thenResponseShouldContainJson();
    }


}
