package ru.yandex.practicum.filmorate.service;

import jakarta.validation.Valid;
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
        User user = userMapper.toUser(userRequestDto);
        validateUserLogin(user);
        log.debug("Запрос на создание пользователя: {} прошел первичные проверки", user.getName());
        user = userStorage.create(user);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
        return userResponseDto;
    }

    public List<UserResponseDto> getAll() {
        return userStorage.getAll().stream()
                .map(userMapper::toUserResponseDto)
                .toList();
    }

    public UserResponseDto update(UserRequestDto userRequestDto) {
        User user = userMapper.toUser(userRequestDto);
        validateUserLogin(user);
        user = userStorage.update(user);
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);
        return userResponseDto;
    }

    public boolean validateUserLogin(User user) {

        if (user.getLogin().contains(" ")) {
            log.info("Вадиация не пройдена: логин {} не может содержать пробелы", user.getLogin());
            throw new ValidationException("логин не может содержать пробелы");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        return true;
    }
}