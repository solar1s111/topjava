package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDao {
    List<MealTo> getAllMeals();
    void addMeal(Meal meal);
    void deleteMeal(int id);
    void updateMeal(int id, Meal meal);
    Meal getMealById(int id);
}
