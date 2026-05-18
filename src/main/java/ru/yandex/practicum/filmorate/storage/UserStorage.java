package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.domain.User;

import java.util.List;

public interface UserStorage {

    User create(User user);

    List<User> getAll();

    User update(User user);

    boolean isUserExists(Long userID);

    boolean addFriend(Long userID, Long friendUserId);

    boolean removeFriend(Long userID, Long friendUserId);
}
