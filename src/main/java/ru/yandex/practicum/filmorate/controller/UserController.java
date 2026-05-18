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
        log.info("Старт обновления пользователя: {}", user.getName());
        return userService.update(user);
    }

    @GetMapping
    public List<UserResponseDto> getAll() {
        return userService.getAll();
    }

    //PUT /users/{id}/friends/{friendId}
    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@RequestParam String id, @RequestParam String friendId) {
        userService.addFriend(id, friendId);
    }

//    DELETE /users/{id}/friends/{friendId}
    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@RequestParam String id, @RequestParam String friendId) {
        userService.removeFriend(id, friendId);
    }

//GET /users/{id}/friends
}
