package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.domain.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserStorage {

    User create(User user);

    List<User> getAll();

    User update(User user);

    boolean addFriend(Long userID, Long friendUserId);

    boolean removeFriend(Long userID, Long friendUserId);

    List<User> getUsersById(Collection<?> usersId);

    Optional<User> findUserById(Long userId);
}
