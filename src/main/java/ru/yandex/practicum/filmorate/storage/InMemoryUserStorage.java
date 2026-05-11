package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundResourseException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@Service
public class InMemoryUserStorage implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private Long currentID = 0L;

    @Override
    public User create(@Valid User user) {
        user.setId(++currentID);
        users.put(user.getId(), user);
        log.debug("пользователь {} создан", user.getName());
        return user;
    }

    @Override
    public List<User> getAll() {
        log.debug("подготовлен список из {} пользователей", users.size());
        return new ArrayList<>(users.values());
    }

    @Override
    public User update(@Valid User user) {
        if (!users.containsKey(user.getId())) {
            log.info("попытка изменить данные пользователя с несуществующим id = {}", user.getId());
            throw new NotFoundResourseException("попытка изменить фильм с несуществующим id = " + user.getId());
        }
        validateUserLogin(user);
        users.put(user.getId(), user);
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