package ru.sharipovar.bookhunter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import ru.sharipovar.bookhunter.domain.BookServicesDTO;
import ru.sharipovar.bookhunter.service.BookServicesService;

@Controller
@RequestMapping("api")
public class APIController {

    @Autowired
    private BookServicesService service;

    @GetMapping("discovery")
    public Flux<BookServicesDTO> getBookServices() {
        return service.getBookServices();
    }

}
