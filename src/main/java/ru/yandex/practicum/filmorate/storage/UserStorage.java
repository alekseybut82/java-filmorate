package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.domain.User;

import java.util.List;

public interface UserStorage {

    public User create(User user);

    public List<User> getAll();

    public User update(User user);
}
