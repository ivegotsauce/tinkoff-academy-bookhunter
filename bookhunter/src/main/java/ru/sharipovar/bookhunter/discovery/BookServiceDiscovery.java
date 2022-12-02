package ru.sharipovar.bookhunter.discovery;

import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.Objects;

public class BookServiceDiscovery implements ServiceDiscovery{

    private WebClient client;

    private String getStringResponse(String url) {
        return client.get().uri(url).retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String discoverService(String url) {
        try {
            client = WebClient.create(url);
            if (Objects.equals(getStringResponse("system/liveness"), "{\"status\":\"UP\"}")) {
                return getStringResponse("system/version");
            }
        } catch (WebClientException ignored) {
            //
        }
        return "is not available";
    }
}
