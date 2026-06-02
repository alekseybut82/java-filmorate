package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundResourceException;
import ru.yandex.practicum.filmorate.model.domain.User;

import java.util.*;

@Slf4j
@NoArgsConstructor
@Component
public class InMemoryUserStorageImpl implements UserStorage {

    private final Map<Long, User> users = new HashMap<>();
    private Long currentID = 0L;

    @Override
    public User create(@Valid User user) {
        user.setId(++currentID);
        users.put(user.getId(), user);
        log.debug("пользователь {} создан, id {}", user.getName(), user.getId());
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
            throw new NotFoundResourceException("попытка изменить пользователя с несуществующим id = " + user.getId());
        }
        users.put(user.getId(), user);
        log.debug("пользователь {}, id {} обновлен", user.getName(), user.getId());
        return user;
    }

    @Override
    public boolean addFriend(Long userID, Long friendUserId) {
        return users.get(userID).getFriendIds().add(friendUserId);
    }

    @Override
    public boolean removeFriend(Long userID, Long friendUserId) {
        return users.get(userID).getFriendIds().remove(friendUserId);
    }

    @Override
    public List<User> getUsersById(Collection<?> usersId) {
        return usersId.stream()
                .map(users::get)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }
}