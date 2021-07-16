package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

public abstract class AbstractMealController {
    protected static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    protected final MealService service;

    public AbstractMealController(MealService service) {
        this.service = service;
    }

    public List<MealTo> abstractGetAllOrBetween(Model model, HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        int userId = SecurityUtil.authUserId();
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        if (checkTimeOnNull(startDate, endDate, startTime, endTime)) {
            log.info("getAll for user {}", userId);
        } else {
            log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        }
        return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);

    }

    public void abstractDelete(int id) {
        int userId = SecurityUtil.authUserId();
        service.delete(id, userId);
        log.info("delete meal {} for user {}", id, userId);
    }

    public void abstractSave(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            assureIdConsistent(meal, getId(request));
            service.update(meal, userId);
            log.info("update {} for user {}", meal, userId);
        } else {
            service.create(meal, SecurityUtil.authUserId());
            log.info("create {} for user {}", meal, userId);
        }
    }

    public int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    public boolean checkTimeOnNull(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return startDate == null && endDate == null && startTime == null && endTime == null;
    }
}
