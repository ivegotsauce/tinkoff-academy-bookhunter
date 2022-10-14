package ru.sharipovar.bookhunter.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.sharipovar.bookhunter.BookHunterApplication;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.domain.UserProfile;

import java.util.*;

@Service
public class UserService {

    private UserProfile userToProfile(User user) {
        UserProfile profile = new UserProfile();
        profile.setAge(String.valueOf(user.getAge()));
        profile.setGender(user.getGender().toString().toLowerCase());
        profile.setLocation(String.join(", ", String.valueOf(user.getLatitude()),
                String.valueOf(user.getLongitude())));
        profile.setName(user.getName());
        profile.setNick(user.getNick());
        return profile;
    }

    public Flux<UserProfile> getNearestUsers(
            Double latitude,
            Double longitude,
            Long distance,
            Long amount,
            UUID id,
            boolean checkId
    ) {
        ArrayList<UserProfile> nearestUsers = new ArrayList<>();
        TreeMap<Long, User> map = new TreeMap<>();
        for (User user : getUsers()) {
            long dist = distanceInKmBetweenEarthCoordinates(latitude, longitude,
                    user.getLatitude(), user.getLongitude());
            map.put(dist, user);
        }
        for (var entry : map.entrySet()) {
            if (entry.getKey() > distance) {
                break;
            }
            User u = entry.getValue();
            if (amount == 0) {
                break;
            }
            if (!checkId || !u.getId().equals(id)) {
                nearestUsers.add(userToProfile(u));
                amount--;
            }
        }
        return Flux.just(nearestUsers.toArray(new UserProfile[Math.min(Math.toIntExact(amount), nearestUsers.size())]));
    }

    private Double degreesToRadians(Double degrees) {
        return degrees * Math.PI / 180;
    }

    private Long distanceInKmBetweenEarthCoordinates(Double lat1, Double lon1, Double lat2, Double lon2) {
        double earthRadiusKm = 6371;

        double dLat = degreesToRadians(lat2 - lat1);
        double dLon = degreesToRadians(lon2 - lon1);

        lat1 = degreesToRadians(lat1);
        lat2 = degreesToRadians(lat2);

        var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return ((long) (earthRadiusKm * c)) + 1;
    }

    public User getUserById(UUID id) {
        return getUsers().stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public List<User> getUsers() {
        return BookHunterApplication.users;
    }


}
