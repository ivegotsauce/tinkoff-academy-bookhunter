package ru.sharipovar.bookhunter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.domain.UserProfile;
import ru.sharipovar.bookhunter.service.UserService;

import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

    private final UserService userService = new UserService();

    @GetMapping("{id}/nearest")
    public Flux<UserProfile> getNearest(
            @PathVariable UUID id,
            @RequestParam(required = false, defaultValue = "100") Long distance,
            @RequestParam(required = false, defaultValue = "50") Long amount
    ) {
        User user = userService.getUserById(id);
        if (user == null) {
            return Flux.just();
        }
        return userService.getNearestUsers(user.getLatitude(), user.getLongitude(), distance, amount, id, true);
    }

    @GetMapping("nearest")
    public Flux<UserProfile> getNearest(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(required = false, defaultValue = "100") Long distance,
            @RequestParam(required = false, defaultValue = "50") Long amount
    ) {
        return userService.getNearestUsers(latitude, longitude, distance, amount, null, false);
    }
}
