package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class  InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Meal> mealsForOneUser = new ConcurrentHashMap<>();
    private final List<User> users = new InMemoryUserRepository().getAll();
    public static final int USER_ID = 2;

    private final AtomicInteger counter = new AtomicInteger(0);

    private final Map<Integer, Map<Integer, Meal>> storageMealsByUser = new ConcurrentHashMap<>();


    {
        MealsUtil.meals.forEach(meal -> mealsForOneUser.put(counter.incrementAndGet(), meal));
        users.forEach(user -> storageMealsByUser.put(user.getId(), new ConcurrentHashMap<>()));
        storageMealsByUser.put(1, mealsForOneUser);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            storageMealsByUser.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return storageMealsByUser.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int mealId, int userId) {
        log.info("delete {} userId {}", mealId, userId);
        return storageMealsByUser.get(userId).remove(mealId) != null;
    }

    @Override
    public Meal get(int mealId, int userId) {
        log.info("get {} userId {}", mealId, userId);
        return storageMealsByUser.get(userId).get(mealId);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("get all for userId {}", userId);
        return storageMealsByUser.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

