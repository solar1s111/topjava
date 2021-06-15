package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalTime;
import java.util.List;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal, int userId);
    // false if meal do not belong to userId
    boolean delete(int mealId, int userId);
    // null if meal do not belong to userId
    Meal get(int mealId, int userId);
    // ORDERED dateTime desc
    List<Meal> getAll(int userId);

    List<MealTo> getAllWithTimeFilter(int userId, LocalTime startTime, LocalTime endTime);
}
