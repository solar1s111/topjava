package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        request.setAttribute("meals", MealsUtil.getMeals());

        request.getRequestDispatcher("meals.jsp").forward(request, response);
        log.info("received table");

        if (action.equals("delete")) {
            String id = request.getParameter("id");
            MealsUtil.deleteMeal(Integer.parseInt(id));
            log.info("meal deleted");
            request.setAttribute("meals", MealsUtil.getMeals());
            request.getRequestDispatcher("meals.jsp").forward(request, response);
        }

    }
}
