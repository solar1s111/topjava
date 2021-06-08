package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealDao {
    void add(Meal meal);
    void delete(int id);
    void update(int id, Meal meal);
    Meal getById(int id);
}
