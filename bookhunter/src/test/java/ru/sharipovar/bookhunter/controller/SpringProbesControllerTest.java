package ru.sharipovar.bookhunter.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SpringProbesControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    private WebTestClient.ResponseSpec responseSpec;

    private void whenDoRequest(String path) {
        responseSpec = webTestClient.get().uri(path).exchange();
    }

    private void thenShouldResponse(String response) {
        responseSpec.expectStatus().is2xxSuccessful().expectBody(String.class).isEqualTo(response);
    }

    @Test
    public void testReadiness() {
        whenDoRequest("/system/readiness");
        thenShouldResponse("{\"status\":\"UP\"}");
    }

    @Test
    public void testLiveness() {
        whenDoRequest("/system/liveness");
        thenShouldResponse("{\"status\":\"UP\"}");
    }

    @Test
    public void testVersion() {
        whenDoRequest("/system/version");
        thenShouldResponse("0.0.1-SNAPSHOT");
    }

}
