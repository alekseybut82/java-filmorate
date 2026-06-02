package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.model.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @PostMapping
    public FilmResponseDto create(@Valid @RequestBody FilmRequestDto film) {
        log.info("Запрос на добавление фильма: {}", film.getName());
        return filmService.create(film);
    }

    @PutMapping
    public FilmResponseDto update(@Valid @RequestBody FilmRequestDto film) {
        log.info("Запрос на обновление фильма: {}", film.getName());
        return filmService.update(film);
    }

    @GetMapping
    public List<FilmResponseDto> getAll() {
        log.info("Запрос на получение списка всех фильмов");
        return filmService.getAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Запрос на установку like для фильма от {} пользователь {}", id, userId);
        filmService.addLike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(@PathVariable String id, @PathVariable String userId) {
        log.info("Запрос на удаление like");
        filmService.removeLike(id, userId);
    }

    @GetMapping("/popular")
    public List<FilmResponseDto> mostPopularFilm(@RequestParam(defaultValue = "10") String count) {
        return filmService.findMostPopularFilm(count);
    }
}
