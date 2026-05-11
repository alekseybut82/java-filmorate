package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import ru.yandex.practicum.filmorate.exception.NotFoundResourseException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.List;

public interface UserStorage {

    public User create(User user);

    public List<User> getAll();

    public User update(User user);
}
