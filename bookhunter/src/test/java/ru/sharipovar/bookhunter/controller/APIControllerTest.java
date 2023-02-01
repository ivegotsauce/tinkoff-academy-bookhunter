package ru.sharipovar.bookhunter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class APIControllerTest extends BaseControllerTest{

    private void thenResponseShouldContainBookServicesDTO() {
        responseSpec.expectAll(
                rSpec -> rSpec.expectStatus().is2xxSuccessful(),
                rSpec -> rSpec.expectBody().jsonPath("$[0].blackbooks").exists()
                        .jsonPath("$[0].bookshelf").exists()
        );
    }

    @Test
    public void testDiscovery() {
        whenDoRequest("api/discovery");
        thenResponseShouldContainBookServicesDTO();
    }

}
