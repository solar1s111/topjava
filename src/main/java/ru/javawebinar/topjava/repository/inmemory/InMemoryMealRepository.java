package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
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
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 1", 1000), 1);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 2", 1000), 2);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 3", 5), 100);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 3", 5), 100);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 3", 5), 100);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 28, 10, 0), "calories for user 3", 1000), 100);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 28, 10, 0), "calories for user 3", 1000), 100);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 28, 10, 0), "calories for user 3", 1000), 100);
        save(new Meal(LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "calories for user 4", 1000), 4);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> mealsByUser = storageMealsByUser.computeIfAbsent(userId, ConcurrentHashMap::new);
        log.info("save {} userId={}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            mealsByUser.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return mealsByUser.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> mealsByUser = storageMealsByUser.get(userId);
        log.info("delete with id={} userId={}", mealId, userId);
        return mealsByUser != null && mealsByUser.remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> mealsByUser = storageMealsByUser.get(userId);
        log.info("get with id={} userId={}", mealId, userId);
        return mealsByUser == null ? null : mealsByUser.get(mealId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> mealsByUser = storageMealsByUser.get(userId);
        log.info("get all for userId={}", userId);
        return mealsByUser.isEmpty() ? Collections.emptyList() : mealsByUser.values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllWithTimeFilter(int userId, LocalDate startTime, LocalDate endTime) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startTime, endTime))
                .collect(Collectors.toList());
    }
}

