package ru.javawebinar.topjava.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserValidator;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;

import javax.validation.Valid;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.getFieldErrors;

@RestController
@RequestMapping(value = "/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUIController extends AbstractUserController {
    @Autowired
    private UserValidator validator;

    @Override
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid UserTo userTo, BindingResult result) {
//        try {
        validator.validate(userTo, result);
        if (result.hasErrors()) {
            // TODO change to exception handler
            throw new IllegalRequestDataException(getFieldErrors(result));
        }
        if (userTo.isNew()) {
            super.create(userTo);
        } else {
            super.update(userTo, userTo.id());
        }
//        } catch (DataIntegrityViolationException e) {
//            throw new DataIntegrityViolationException("User with this email already exists");
//        }
    }

    @Override
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        super.enable(id, enabled);
    }
}
