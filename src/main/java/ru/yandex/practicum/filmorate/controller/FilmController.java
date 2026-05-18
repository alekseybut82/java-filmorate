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
        log.info("Старт добавления фильма: {}", film.getName());
        return filmService.create(film);
    }

    @PutMapping
    public FilmResponseDto update(@Valid @RequestBody FilmRequestDto film) {
        log.info("Старт обновления фильма: {}", film.getName());
        return filmService.update(film);
    }

    @GetMapping
    public List<FilmResponseDto> getAll() {
        return filmService.getAll();
    }

}
