package ru.javawebinar.topjava.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class UserValidator implements Validator {
    @Autowired
    private MessageSource message;

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Map<String, User> users = service.getAll().stream()
                .collect(Collectors.toMap(User::getEmail, user -> user));

        UserTo userTo = (UserTo) target;
        String email = userTo.getEmail();

        if (users.containsKey(email)) {
            errors.rejectValue("email", "", message.getMessage("user.valid.email", null, Locale.getDefault()));
        }
    }
}