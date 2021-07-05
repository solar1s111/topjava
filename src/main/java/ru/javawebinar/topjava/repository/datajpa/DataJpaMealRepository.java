package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepository implements MealRepository {
    private static final Sort SORT_DATE_TIME = Sort.by(Sort.Direction.DESC, "dateTime");

    private final CrudMealRepository crudRepository;

    private final EntityManager em;

    public DataJpaMealRepository(CrudMealRepository crudRepository, EntityManager em) {
        this.crudRepository = crudRepository;
        this.em = em;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
//        meal.setUser(crudRepository.getById(userId).getUser());
        meal.setUser(em.getReference(User.class, userId));
        if (meal.isNew()) {
            return crudRepository.saveAndFlush(meal);
        } else if (get(meal.id(), userId) == null) {
            return null;
        }
        return crudRepository.saveAndFlush(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllByUserId(SORT_DATE_TIME, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.getBetweenHalfOpen(SORT_DATE_TIME, startDateTime, endDateTime, userId);
    }
}
