package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.util.Arrays;

public class SpringMain {

    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

//            System.out.println(mealRestController.create(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "for user 1", 500),4));
//            mealRestController.update(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "FOR USER 1", 700), 4,4);
//            mealRestController.create(new Meal(null, LocalDateTime.of(2021, Month.MAY, 30, 10, 0), "FOR USER 1 (2 meal)", 700), 4);
            mealRestController.getAll(4).forEach(System.out::println);
            mealRestController.delete(1, 10); //no NPE
        }
    }
}
