package ru.job4j.forum.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.forum.model.Authority;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AuthorityRepository {
    private final Map<Integer, Authority> authorities = new HashMap<>();
    private final AtomicInteger authorityId = new AtomicInteger(2);

    public AuthorityRepository() {
        authorities.put(1, Authority.of("ROLE_ADMIN"));
        authorities.put(2, Authority.of("ROLE_USER"));
    }

    public void save(Authority authority) {
        if (authority.getId() == 0) {
            authority.setId(authorityId.incrementAndGet());
        }
        authorities.put(authority.getId(), authority);
    }

    public Authority findByAuthority(String authorityName) {
        return authorities.values().stream()
                .filter(authority -> authorityName.equals(authority.getName()))
                .findFirst().orElse(null);
    }
}
