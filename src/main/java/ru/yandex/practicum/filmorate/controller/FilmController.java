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

    private final FilmsHandler filmsHandler;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Старт добавления фильма: {}", film.getName());
        return filmsHandler.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Старт обновления фильма: {}", film.getName());
        return filmsHandler.update(film);
    }

    @GetMapping
    public List<Film> getAll() {
        return filmsHandler.getAll();
    }

}
