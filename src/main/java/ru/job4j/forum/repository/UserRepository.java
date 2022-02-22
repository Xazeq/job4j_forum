package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UserRepository {
    private final Map<Integer, User> users = new HashMap<>();
    private final AtomicInteger userId = new AtomicInteger(1);

    public UserRepository() {
        }

    public void saveUser(User user) {
        if (user.getId() == 0) {
            user.setId(userId.incrementAndGet());
        }
        users.put(user.getId(), user);
    }

    public boolean existsByUsername(String username) {
        return users.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    public User findUserByUsername(String username) {
        return users.values().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .orElse(null);
    }
}
