package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class MealsUtil {
    private static final int CALORIES_PER_DAY = 2000;

    private static Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    static {
        Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        meal.setId(CountId.getId());
        meals.put(meal.getId(), meal);
        Meal meal1 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
        meal1.setId(CountId.getId());
        meals.put(meal1.getId(), meal1);
        Meal meal2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
        meal2.setId(CountId.getId());
        meals.put(meal2.getId(), meal2);
        Meal meal3 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
        meal3.setId(CountId.getId());
        meals.put(meal3.getId(), meal3);
        Meal meal4 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
        meal4.setId(CountId.getId());
        meals.put(meal4.getId(), meal4);
        Meal meal5 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
        meal5.setId(CountId.getId());
        meals.put(meal5.getId(), meal5);
        Meal meal6 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);
        meal6.setId(CountId.getId());
        meals.put(meal6.getId(), meal6);
    }


    public static Map<Integer, Meal> getMeals() {
        return meals;
    }

    public static List<MealTo> getMealsTo() {
        List<Meal> mealList = new ArrayList<>();
        for (Map.Entry<Integer, Meal> mealEntry : meals.entrySet()) {
            mealList.add(mealEntry.getValue());
        }
        return filteredByStreams(mealList, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
