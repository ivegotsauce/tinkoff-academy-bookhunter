package ru.sharipovar.bookhunter.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import ru.sharipovar.bookhunter.domain.User;

import java.util.Random;
import java.util.UUID;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;
    private final Random random = new Random();
    private User user;
    private User currentUser;
    private UUID id;
    private void givenUser() {
        var users = userService.getUsers();
        user = users.get(random.nextInt(users.size()));
    }

    private void whenFindUserById() {
        currentUser = userService.getUserById(user.getId());
    }

    private void whenUpdateUser() {
        currentUser = UserService.constructUser(user.getId(), user.getNick(), user.getName(),
                user.getAge(), user.getGender(), user.getLatitude(), user.getLongitude());
        user.setName(user.getName() + "A");
        userService.updateUser(user);
    }

    private void thenUserNotEquals() {
        assertNotEquals(currentUser, user);
    }

    private void thenUserEquals() {
        assertEquals(user, currentUser);
    }

    private void givenUserId() {
        id = UUID.randomUUID();
    }

    private void whenCreateUser() {
        userService.createUser(id, "a", "a", 1, User.Gender.HIDDEN, 0, 0);
    }

    private void thenUserExists() {
        assertNotNull(userService.getUserById(id));
    }

    private void whenDeleteUser() {
        id = user.getId();
        userService.deleteUser(id);
    }

    private void thenUserNotExists() {
        assertNull(userService.getUserById(id));
    }

    @Test
    public void testReadUser() {
        givenUser();
        whenFindUserById();
        thenUserEquals();
    }

    @Test
    public void testCreateUser() {
        givenUserId();
        whenCreateUser();
        thenUserExists();
    }

    @Test
    public void testUpdateUser() {
        givenUser();
        whenUpdateUser();
        thenUserNotEquals();
    }

    @Test
    public void testDeleteUser() {
        givenUser();
        whenDeleteUser();
        thenUserNotExists();
    }

}
