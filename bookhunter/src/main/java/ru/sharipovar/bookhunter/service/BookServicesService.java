package ru.sharipovar.bookhunter.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.sharipovar.bookhunter.discovery.BookServiceDiscovery;
import ru.sharipovar.bookhunter.discovery.ServiceDiscovery;
import ru.sharipovar.bookhunter.domain.BookServicesDTO;


@Service
public class BookServicesService {
    private final ServiceDiscovery discovery = new BookServiceDiscovery();

    private String getBookServiceResponse(String serviceName, String url) {
        String response = discovery.discoverService(url);
        if (response.equals("is not available")) {
            return String.join(" ", serviceName, response);
        }
        return response;
    }
    public Flux<BookServicesDTO> getBookServices() {
        String bookShelfURL = "http://localhost:8070";
        String blackBooksURL = "http://localhost:8090";
        return Flux.just(new BookServicesDTO(
                getBookServiceResponse("blackbooks", blackBooksURL),
                getBookServiceResponse("bookshelf", bookShelfURL)));
    }
}
