package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping
    public String getAllOrBetween(Model model, HttpServletRequest request) {
        model.addAttribute("meals", abstractGetAllOrBetween(model, request));
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        abstractDelete(id);
        return "redirect:/meals";
    }

    @GetMapping({"/editForm", "/addForm"})
    public String showAddEditForm(@RequestParam(value = "id", required = false) Integer id, Model model) {
        Meal meal = id == null ? new Meal() : service.get(id, SecurityUtil.authUserId());
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        abstractSave(request);
        return "redirect:/meals";
    }
}
