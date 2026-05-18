package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.domain.User;
import ru.yandex.practicum.filmorate.model.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.model.dto.UserResponseDto;
import ru.yandex.practicum.filmorate.service.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserStorage userStorage;
    private final UserMapper userMapper;

    public UserResponseDto create(UserRequestDto userRequestDto) {
        return userMapper.toUserResponseDto(userStorage.create(validateUserLogin(userMapper.toUser(userRequestDto))));
    }

    public List<UserResponseDto> getAll() {
        return userStorage.getAll().stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto update(UserRequestDto userRequestDto) {
        return userMapper.toUserResponseDto(userStorage.update(validateUserLogin(userMapper.toUser(userRequestDto))));
    }

    public User validateUserLogin(User user) {

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

        Long userID = Long.valueOf(id);
        Long friendUserId = Long.valueOf(friendId);

        if (!userStorage.isUserExists(userID)) {
            /// исключение
        } else if (!userStorage.isUserExists(friendUserId)) {
            /// исключение
        }

        userStorage.addFriend(userID, friendUserId);
        userStorage.addFriend(userID, friendUserId);
    }

    public void removeFriend(String id, String friendId) {

        Long userID = Long.valueOf(id);
        Long friendUserId = Long.valueOf(friendId);

        if (!userStorage.isUserExists(userID)) {
            /// исключение
        } else if (!userStorage.isUserExists(friendUserId)) {
            /// исключение
        }

        if (!userStorage.removeFriend(userID, friendUserId)) {
            log.info("Пользовать id = {} не являлся другом пользователя id = {}, операция удаления из друзей не выполнялась",
                    friendId, id);
        }

        if (!userStorage.removeFriend(friendUserId, userID)) {
            log.info("Пользовать id = {} не являлся другом пользователя id = {}, операция удаления из друзей не выполнялась",
                    id, friendId);
        }
    }
}