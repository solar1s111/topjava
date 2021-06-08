package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealDao mealDao;

    public MealServlet() {
        this.mealDao = new MealDaoInMemory();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        switch (action) {
            case "/add":
                showAddForm(request, response);
                break;
            case "/insert":
                addMeal(request, response);
                break;
            case "/delete":
                deleteMeal(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateMeal(request, response);
                break;
            default:
                listMeal(request, response);
                break;
        }
    }

    private void listMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setAttribute("meals", MealsUtil.getMealsTo());
        request.getRequestDispatcher("meals.jsp").forward(request, response);
        log.info("table received");
    }

    private void showAddForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("edit-meal.jsp").forward(request, response);
        log.info("add form received");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        Meal existingMeal = mealDao.getById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("edit-meal.jsp");
        request.setAttribute("meal", existingMeal);
        dispatcher.forward(request, response);
        log.info("edit form received");
    }

    private void addMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        mealDao.add(new Meal(date, description, calories));
        response.sendRedirect("meals");
        log.info("meal added");
    }

    private void updateMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        request.setCharacterEncoding("UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime date = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        mealDao.update(id, new Meal(date, description, calories));
        response.sendRedirect("meals");
        log.info("meal updated");
    }

    private void deleteMeal(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        mealDao.delete(id);
        response.sendRedirect("meals");
        log.info("meal deleted");
    }
}
