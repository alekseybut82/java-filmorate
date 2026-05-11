package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserHandler userHandler;
    private Long currentID = 0L;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info("Старт добавления пользователя: {}", user.getName());
        return userHandler.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Старт обновления пользователя: {}", user.getName());
        return userHandler.update(user);
    }

    @GetMapping
    public List<User> getAll() {
        return userHandler.getAll();
    }

}
