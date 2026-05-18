package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundResourceException;
import ru.yandex.practicum.filmorate.model.domain.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@NoArgsConstructor
@Component
public class InMemoryFilmStorageImpl implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private Long currentID = 0L;

    @Override
    public Film create(@Valid Film film) {
        film.setId(++currentID);
        films.put(film.getId(), film);
        log.debug("фильм {} создан", film.getName());
        return film;
    }

    @Override
    public List<Film> getAll() {
        log.debug("подготовлен список из {} фильмов", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film update(@Valid Film film) {
        if (!films.containsKey(film.getId())) {
            log.info("попытка изменить фильм с несуществующим id = {}", film.getId());
            throw new NotFoundResourceException("попытка изменить фильм с несуществующим id = " + film.getId());
        }
        films.put(film.getId(), film);
        return film;
    }
}