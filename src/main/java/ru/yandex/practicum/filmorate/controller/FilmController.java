package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Старт добавления фильма: {}", film.getName());
        return inMemoryFilmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Старт обновления фильма: {}", film.getName());
        return inMemoryFilmStorage.update(film);
    }

    @GetMapping
    public List<Film> getAll() {
        return inMemoryFilmStorage.getAll();
    }

}
