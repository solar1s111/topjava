package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getForUser() {
        Meal meal = service.get(mealForUser.getId(), USER_ID);
        assertMatch(meal, mealForUser);
    }

    @Test
    public void getForAdmin() {
        Meal meal = service.get(mealForAdmin.getId(), ADMIN_ID);
        assertMatch(meal, mealForAdmin);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(mealForUser.getId(), ADMIN_ID));
    }

    @Test
    public void deleteForUser() {
        service.delete(mealForUser.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(mealForUser.getId(), USER_ID));
    }

    @Test
    public void deleteForAdmin() {
        service.delete(mealForAdmin.getId(), ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(mealForAdmin.getId(), ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(mealForUser.getId(), ADMIN_ID));
    }

    @Test
    public void getAllForUser() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, mealForUser, mealForUser2);
    }

    @Test
    public void getAllForAdmin() {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertMatch(all, mealForAdmin, mealForAdmin2);
    }

    @Test
    public void updateForUser() {
        Meal updated = getUpdatedForUser();
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), getUpdatedForUser());
    }

    @Test
    public void updateForAdmin() {
        Meal updated = getUpdatedForAdmin();
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(updated.getId(), ADMIN_ID), getUpdatedForAdmin());
    }

    @Test
    public void updateNotFound() {
        assertThrows(NotFoundException.class, () -> service.update(getUpdatedForUser(), ADMIN_ID));
    }

    @Test
    public void createForUser() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void createForAdmin() {
        Meal created = service.create(getNew(), ADMIN_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, LocalDateTime.of(2021, Month.JUNE, 19, 10, 0, 0),
                        "Duplicate", 500), USER_ID));
    }

    @Test
    public void getBetweenInclusiveForUser() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(mealForUser.getDate(),
                mealForUser.getDate(), USER_ID);
        assertMatch(betweenInclusive, mealForUser);
    }

    @Test
    public void getBetweenInclusiveForAdmin() {
        List<Meal> betweenInclusive = service.getBetweenInclusive(mealForAdmin.getDate(),
                mealForAdmin.getDate(), ADMIN_ID);
        assertMatch(betweenInclusive, mealForAdmin);
    }
}