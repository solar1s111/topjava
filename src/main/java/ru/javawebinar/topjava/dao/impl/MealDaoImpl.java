package ru.javawebinar.topjava.dao.impl;

import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

public class MealDaoImpl implements MealDao {
    @Override
    public List<MealTo> getAllMeals() {
        return MealsUtil.getMealsTo();
    }

    @Override
    public void addMeal(Meal meal) {
        List<Meal> mealList = MealsUtil.getMeals();
        mealList.add(meal);
    }

    @Override
    public void deleteMeal(int id) {
        List<Meal> mealList = MealsUtil.getMeals();
        mealList.remove(getMealById(id));
    }

    @Override
    public void updateMeal(int id, Meal meal) {
        Meal editMeal = getMealById(id);
        editMeal.setDateTime(meal.getDateTime());
        editMeal.setDescription(meal.getDescription());
        editMeal.setCalories(meal.getCalories());
    }

    @Override
    public Meal getMealById(int id) {
        List<Meal> meals = MealsUtil.getMeals();
        return meals.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
