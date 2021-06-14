package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Map<Integer, Meal>> storageMealsByUser = new ConcurrentHashMap<>();

    {
        List<User> users = new InMemoryUserRepository().getAll();
        users.forEach(user -> storageMealsByUser.put(user.getId(), new ConcurrentHashMap<>()));
        save(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 1", 1000), 1);
        save(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 2", 1000), 2);
        save(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 3", 1000), 3);
//        save(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 4", 1000), 4);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} userId={}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            storageMealsByUser.getOrDefault(userId, new ConcurrentHashMap<>()).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return storageMealsByUser.getOrDefault(userId, new ConcurrentHashMap<>()).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.info("delete with id={} userId={}", mealId, userId);
        return storageMealsByUser.getOrDefault(userId, new ConcurrentHashMap<>()).remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("get with id={} userId={}", mealId, userId);
        return storageMealsByUser.getOrDefault(userId, new ConcurrentHashMap<>()).get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("get all for userId={}", userId);
        return storageMealsByUser.getOrDefault(userId, new ConcurrentHashMap<>()).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<MealTo> getAllWithTimeFilter(int userId, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredTos(getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }
}

