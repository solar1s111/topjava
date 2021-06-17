package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
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

    public List<MealTo> getAll() {
        log.info("get all for userId={}", SecurityUtil.authUserId());
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealTo> getAllWithTimeFilter(@Nullable LocalTime startTime, @Nullable LocalDate startDate,
                                             @Nullable LocalTime endTime, @Nullable LocalDate endDate) {
        log.info("get all with time filter start={} end={} for userId={}", SecurityUtil.authUserId(), startTime, endTime);
        List<Meal> mealsFiltered =  service.getAllWithTimeFilter(SecurityUtil.authUserId(), startDate, endDate);
        return MealsUtil.getFilteredTos(mealsFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }

    public Meal get(int id) {
        log.info("get with id={} userId={}", id, SecurityUtil.authUserId());
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        log.info("create {} userId={}", meal, SecurityUtil.authUserId());
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
        log.info("delete with id={} userId={}", id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
        log.info("update {} with id={} userId {}", meal, id, SecurityUtil.authUserId());
    }
}