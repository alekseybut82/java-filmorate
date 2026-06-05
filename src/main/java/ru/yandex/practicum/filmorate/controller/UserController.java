package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.model.dto.UserResponseDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDto create(@Valid @RequestBody UserRequestDto user) {
        log.info("Получен post запрос на добавления пользователя: {}", user.getName());
        return userService.create(user);
    }

    @PutMapping
    public UserResponseDto update(@Valid @RequestBody UserRequestDto user) {
        log.info("Запрос на обновление пользователя: {}", user.getName());
        return userService.update(user);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        log.info("Получен запрос всех пользователей");
        return userService.getAll();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос на добавления друга {} для пользователя {}", friendId, id);
        userService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable Long id, @PathVariable Long friendId) {
        log.info("Получен запрос на удаление друга {} для пользователя {}", friendId, id);
        userService.removeFriend(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<UserResponseDto> getFriends(@PathVariable Long id) {
        log.info("Получен запрос списка друзей для {}", id);
        return userService.getUserFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserResponseDto> getCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        log.info("Получен запрос списка общих друзей пользователя {} с пользователем {}", otherId, id);
        return userService.getCommonFriends(id, otherId);
    }
}
