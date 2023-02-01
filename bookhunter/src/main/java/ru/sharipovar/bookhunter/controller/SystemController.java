package ru.sharipovar.bookhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private ApplicationContext context;
    private final String URL = "http://localhost:8080";
    private final WebClient client = WebClient.create(URL);

    private Mono<String> getMono(String request) {
        return client.get().uri(request).retrieve().bodyToMono(String.class);
    }
    @GetMapping("liveness")
    public Mono<String> getLiveness() {
        return getMono("/actuator/health/liveness");
    }

    @GetMapping("readiness")
    public Mono<String> getReadiness() {
        return getMono("/actuator/health/readiness");
    }

    @GetMapping("version")
    public String getVersion() {
        return context.getBean(BuildProperties.class).getVersion();
    }

}
