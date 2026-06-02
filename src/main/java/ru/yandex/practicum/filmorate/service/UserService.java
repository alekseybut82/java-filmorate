package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundResourceException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.domain.User;
import ru.yandex.practicum.filmorate.model.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.model.dto.UserResponseDto;
import ru.yandex.practicum.filmorate.service.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;
    private final UserMapper userMapper;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        return userMapper.toUserResponseDto(userStorage.create(applyUserDefaultsAndValidate(userMapper.toUser(userRequestDto))));
    }

    public List<UserResponseDto> getAll() {
        return userStorage.getAll().stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto update(UserRequestDto userRequestDto) {
        return userMapper.toUserResponseDto(userStorage.update(applyUserDefaultsAndValidate(userMapper.toUser(userRequestDto))));
    }

    public User applyUserDefaultsAndValidate(User user) {

        if (user.getLogin().contains(" ")) {
            log.info("Валидация не пройдена: логин {} не может содержать пробелы", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return user;
    }

    public void addFriend(String id, String friendId) {

        var userID = transformID(id);
        var friendUserId = transformID(friendId);

        userStorage.findUserById(userID)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userID + " не найден"));

        userStorage.findUserById(friendUserId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + friendUserId + " не найден"));

        userStorage.addFriend(userID, friendUserId);
        userStorage.addFriend(friendUserId, userID);
    }

    public void removeFriend(String id, String friendId) {

        var userID = transformID(id);
        var friendUserId = transformID(friendId);

        userStorage.findUserById(userID)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userID + " не найден"));

        userStorage.findUserById(friendUserId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + friendUserId + " не найден"));

        if (!userStorage.removeFriend(userID, friendUserId)) {
            log.warn("Пользовать id = {} не являлся другом пользователя id = {}, операция удаления из друзей не выполнялась",
                    friendId, id);
        }

        if (!userStorage.removeFriend(friendUserId, userID)) {
            log.warn("Пользовать id = {} не являлся другом пользователя id = {}, операция удаления из друзей не выполнялась",
                    id, friendId);
        }
    }

    public List<UserResponseDto> getUserFriends(String id) {

        var userID = transformID(id);

        Set<Long> friendsID = userStorage.findUserById(userID)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userID + " не найден"))
                .getFriendIds();

        return userStorage.getUsersById(friendsID).stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public List<UserResponseDto> getCommonFriends(String id, String otherId) {

        var userId = transformID(id);
        var otherUserId = transformID(otherId);

        Set<Long> userFriendsId = userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userId + " не найден"))
                .getFriendIds();

        Set<Long> otherUserFriendsId = userStorage.findUserById(otherUserId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + otherUserId + " не найден"))
                .getFriendIds();

        userFriendsId.retainAll(otherUserFriendsId);

        return userStorage.getUsersById(userFriendsId).stream()
                .map(userMapper::toUserResponseDto)
                .toList();

    }

    public boolean isUserAbsent(Long userId) {
        if (userStorage.findUserById(userId).isPresent())
            return false;
        else
            return true;
    }

    public Long transformID(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Некорректный формат Id " + id);
        }
    }
}