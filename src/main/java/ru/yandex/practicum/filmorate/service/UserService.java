package ru.yandex.practicum.filmorate.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundResourseException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;

    public User create(@Valid User user) {
        validateUserLogin(user);
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.debug("Запрос на создание пользователя: {} прошел первичные проверки", user.getName());
        userStorage.create(user);
        return user;
    }

    public List<User> getAll() {
        log.debug("подготовлен список из {} пользователей", userStorage.size());
        return new ArrayList<>(userStorage.values());
    }

    public User update(@Valid User user) {
        if (!userStorage.containsKey(user.getId())) {
            log.info("попытка изменить данные пользователя с несуществующим id = {}", user.getId());
            throw new NotFoundResourseException("попытка изменить фильм с несуществующим id = " + user.getId());
        }
        validateUserLogin(user);
        userStorage.put(user.getId(), user);
        return user;
    }

    public boolean validateUserLogin(User user) {

        if (!user.getLogin().contains(" ")) {
            return true;
        } else {
            log.info("Вадиация не пройдена: логин {} не может содержать пробелы", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }
    }
}