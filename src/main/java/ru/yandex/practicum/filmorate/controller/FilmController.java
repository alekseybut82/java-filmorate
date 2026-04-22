package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmHandler filmHandler;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Старт добавления фильма: {}", film.getName());
        return filmHandler.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Старт обновления фильма: {}", film.getName());
        return filmHandler.update(film);
    }

    @GetMapping
    public List<Film> getAll() {
        return filmHandler.getAll();
    }

}
