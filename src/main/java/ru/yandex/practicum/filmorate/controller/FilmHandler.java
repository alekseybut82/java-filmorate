package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundResourseException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@Service
public class FilmHandler {

    private final Map<Long, Film> films = new HashMap<>();
    private Long currentID = 0L;

    private static final LocalDate FIST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public Film create(@Valid Film film) {
        validateReleaseDate(film);
        film.setId(++currentID);
        films.put(film.getId(), film);
        log.debug("фильм {} создан", film.getName());
        return film;
    }

    public List<Film> getAll() {
        log.debug("подготовлен список из {} фильмов", films.size());
        return new ArrayList<>(films.values());
    }

    public Film update(@Valid Film film) {
        if (!films.containsKey(film.getId())) {
            log.info("попытка изменить фильм с несуществующим id = {}", film.getId());
            throw new NotFoundResourseException("попытка изменить фильм с несуществующим id = " + film.getId());
        }
        validateReleaseDate(film);
        films.put(film.getId(), film);
        return film;
    }

    public void validateReleaseDate(Film film) {

        if (!film.getReleaseDate().isBefore(FIST_RELEASE_DATE)) {
            return;
        } else {
            log.info("Вадиация не пройдена: дата релиза {}  раньше 28 декабря 1895 года", film.getReleaseDate());
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }

    }
}