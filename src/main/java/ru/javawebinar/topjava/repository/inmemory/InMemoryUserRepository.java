package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();

    private final AtomicInteger counter = new AtomicInteger(0);

    {
        List<User> users = new ArrayList<>();
        users.add(new User(null, "Admin", "admin@gmail.com", "123456", Role.USER, Role.ADMIN));
        users.add(new User(null, "Pol", "pol@gmail.com", "159357", Role.USER));
        users.add(new User(null, "Mike", "mike@gmail.com", "852456", Role.USER));
        users.add(new User(null, "John", "john@gmail.com", "753951", Role.USER));
        users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("get all");
        return repository.values().stream()
                .sorted(Comparator.comparing(User::getName, String::compareToIgnoreCase)
                        .thenComparing(User::getEmail, String::compareToIgnoreCase))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
