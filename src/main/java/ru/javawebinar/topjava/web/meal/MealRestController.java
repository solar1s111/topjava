package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    private MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll(int userId) {
        log.info("get all for userId={}", userId);
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllWithTimeFilter(int userId, LocalTime startTime, LocalTime endTime) {
        log.info("get all with time filter start={} end={} for userId={}", userId, startTime, endTime);
        return service.getAllWithTimeFilter(userId, startTime, endTime);
    }

    public Meal get(int id, int userId) {
        log.info("get with id={} userId={}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        checkNew(meal);
        log.info("create {} userId={}", meal, userId);
        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        service.delete(id, userId);
        log.info("delete with id={} userId={}", id, userId);
    }

    public void update(Meal meal, int id, int userId) {
        assureIdConsistent(meal, id);
        service.update(meal, userId);
        log.info("update {} with id={} userId {}", meal, id, userId);
    }
}