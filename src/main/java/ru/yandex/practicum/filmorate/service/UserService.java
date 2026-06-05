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
        User userRequest = userMapper.toUser(userRequestDto);
        applyUserDefaultsAndValidate(userRequest);
        User userResponse = userStorage.create(userRequest);
        return userMapper.toUserResponseDto(userResponse);
    }

    public List<UserResponseDto> getAll() {
        return userStorage.getAll().stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto update(UserRequestDto userRequestDto) {
        User userRequest = userMapper.toUser(userRequestDto);
        applyUserDefaultsAndValidate(userRequest);
        User userResponse = userStorage.update(userRequest);
        return userMapper.toUserResponseDto(userResponse);
    }

    private User applyUserDefaultsAndValidate(User user) {

        if (user.getLogin().contains(" ")) {
            log.info("Валидация не пройдена: логин {} не может содержать пробелы", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return user;
    }

    public void addFriend(Long userId, Long friendId) {

        userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userId + " не найден"));

        userStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + friendId + " не найден"));

        userStorage.addFriend(userId, friendId);
        userStorage.addFriend(friendId, userId);
    }

    public void removeFriend(Long userId, Long friendId) {

        userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userId + " не найден"));

        userStorage.findUserById(friendId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + friendId + " не найден"));

        if (!userStorage.removeFriend(userId, friendId)) {
            log.warn("Пользовать id = {} не являлся другом пользователя id = {}, операция удаления из друзей не выполнялась",
                    friendId, userId);
        }

        if (!userStorage.removeFriend(friendId, userId)) {
            log.warn("Пользовать id = {} не являлся другом пользователя id = {}, операция удаления из друзей не выполнялась",
                    userId, friendId);
        }
    }

    public List<UserResponseDto> getUserFriends(Long userId) {

        Set<Long> friendsId = userStorage.findUserById(userId)
                .orElseThrow(() -> new NotFoundResourceException("Пользователь id = " + userId + " не найден"))
                .getFriendIds();

        return userStorage.getUsersById(friendsId).stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public List<UserResponseDto> getCommonFriends(Long userId, Long otherUserId) {

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
        return userStorage.findUserById(userId).isEmpty();
    }

}