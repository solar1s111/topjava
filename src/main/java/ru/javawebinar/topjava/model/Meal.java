package ru.javawebinar.topjava.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.ALL_SORTED_BY_USER_ID, query = "SELECT m FROM Meal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.FILTER_BY_USER_ID, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.dateTime >=:startDateTime AND m.dateTime <:endDateTime ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.BY_USER_ID, query = "SELECT m FROM Meal m WHERE m.user.id=:userId AND m.id=:id"),
        @NamedQuery(name = Meal.DELETE_BY_USER_ID, query = "DELETE FROM Meal m WHERE m.user.id=:userId AND m.id=:id")
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = "date_time", name = "meals_unique_user_datetime_idx")})
public class Meal extends AbstractBaseEntity {
    public static final String ALL_SORTED_BY_USER_ID = "Meal.getAllSortedByUserId";

    public static final String FILTER_BY_USER_ID = "Meal.getBetweenHalfOpen";

    public static final String BY_USER_ID = "Meal.getByUserId";

    public static final String DELETE_BY_USER_ID = "Meal.deleteByUserId";

    @Column(name = "date_time", nullable = false, unique = true)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
