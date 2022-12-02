package ru.sharipovar.bookhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BaseControllerTest {
    @Autowired
    public WebTestClient webTestClient;
    public WebTestClient.ResponseSpec responseSpec;

    public void whenDoRequest(String path) {
        responseSpec = webTestClient.get().uri(path).exchange();
    }

}
