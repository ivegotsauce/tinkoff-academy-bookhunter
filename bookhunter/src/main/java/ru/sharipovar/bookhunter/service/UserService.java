package ru.sharipovar.bookhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.sharipovar.bookhunter.domain.User;
import ru.sharipovar.bookhunter.domain.UserProfile;
import ru.sharipovar.bookhunter.repository.UserRepository;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    private static class PairDistanceUser {
        private Long distance;
        private User user;

        public PairDistanceUser(Long distance, User user) {
            this.distance = distance;
            this.user = user;
        }

        public Long getDistance() {
            return distance;
        }

        public void setDistance(Long distance) {
            this.distance = distance;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

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
        List<PairDistanceUser> list = new ArrayList<>();

        for (User user : getUsers()) {
            long dist = distanceInKmBetweenEarthCoordinates(latitude, longitude,
                    user.getLatitude(), user.getLongitude());
            list.add(new PairDistanceUser(dist, user));
        }
        list.sort(Comparator.comparing(PairDistanceUser::getDistance));
        for (var p : list) {
            if (p.getDistance() > distance) {
                break;
            }
            User u = p.getUser();
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
        return ((long) (earthRadiusKm * c));
    }

    public User getUserById(UUID id) {
        return getUsers().stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void createUser(UUID id, String nick, String name, long age, User.Gender gender, double latitude,
                           double longitude) {
        if (getUserById(id) != null) {
            System.out.println("User id is already used");
            return;
        }

        User user = constructUser(id, nick, name, age, gender, latitude, longitude);
        getUsers().add(user);
    }

    public static User constructUser(UUID id, String nick, String name, long age, User.Gender gender, double latitude,
                                     double longitude) {
        User user = new User();
        user.setId(id);
        user.setNick(nick);
        user.setName(name);
        user.setAge(age);
        user.setGender(gender);
        user.setLatitude(latitude);
        user.setLongitude(longitude);
        return user;
    }

    public void updateUser(User user) {
        List<User> users = getUsers();
        int i = users.indexOf(user);
        if (i == -1) {
            System.out.println("No such user");
            return;
        }
        users.set(i, user);
    }

    public void deleteUser(UUID id) {
        getUsers().removeIf(u -> u.getId().equals(id));
    }


}
