package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.CountId;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Map;

public class MealDaoInMemory implements MealDao {

    @Override
    public void add(Meal meal) {
        Map<Integer, Meal> meals = MealsUtil.getMeals();
        meal.setId(CountId.getId());
        meals.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        Map<Integer, Meal> meals = MealsUtil.getMeals();
        Meal meal = getById(id);
        meals.remove(id, meal);
    }

    @Override
    public void update(int id, Meal meal) {
        Meal editMeal = getById(id);
        editMeal.setDateTime(meal.getDateTime());
        editMeal.setDescription(meal.getDescription());
        editMeal.setCalories(meal.getCalories());
    }

    @Override
    public Meal getById(int id) {
        Map<Integer, Meal> meals = MealsUtil.getMeals();
        return meals.get(id);
    }
}
