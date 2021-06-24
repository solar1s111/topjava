package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;

    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int MEAL_ID_1 = ADMIN_ID + 1;

    public static final int MEAL_ID_2 = ADMIN_ID + 2;

    public static final int MEAL_ID_3 = ADMIN_ID + 3;

    public static final int MEAL_ID_4 = ADMIN_ID + 4;

    public static final Meal mealForUser = new Meal(MEAL_ID_1, LocalDateTime.of(2021, Month.JUNE, 18, 10, 0, 0), "Завтрак Юзера", 1);
    public static final Meal mealForUser2 = new Meal(MEAL_ID_2, LocalDateTime.of(2021, Month.JUNE, 19, 10, 0, 0), "Завтрак Юзера", 700);

    public static final Meal mealForAdmin = new Meal(MEAL_ID_3, LocalDateTime.of(2021, Month.JUNE, 18, 10, 0, 0), "Завтрак Админа", 1000);
    public static final Meal mealForAdmin2 = new Meal(MEAL_ID_4, LocalDateTime.of(2021, Month.JUNE, 19, 10, 0, 0), "Завтрак Админа", 200);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2021, Month.JUNE, 21, 10, 0, 0), "Новый завтрак", 2000);
    }

    public static Meal getUpdatedForUser() {
        Meal updated = new Meal(mealForUser);
        updated.setDateTime(LocalDateTime.of(2021, Month.MAY, 10, 10, 10, 10));
        updated.setDescription("Обновленный завтрак");
        updated.setCalories(5000);
        return updated;
    }

    public static Meal getUpdatedForAdmin() {
        Meal updated = new Meal(mealForAdmin);
        updated.setDateTime(LocalDateTime.of(2021, Month.MAY, 20, 20, 10, 10));
        updated.setDescription("Обновленный ужин");
        updated.setCalories(3000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }
}
