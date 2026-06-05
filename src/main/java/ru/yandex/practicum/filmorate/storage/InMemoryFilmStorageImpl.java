package ru.yandex.practicum.filmorate.storage;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundResourceException;
import ru.yandex.practicum.filmorate.model.domain.Film;

import java.util.*;

@Slf4j
@NoArgsConstructor
@Component
public class InMemoryFilmStorageImpl implements FilmStorage {

    private final Map<Long, Film> films = new HashMap<>();
    private Long currentID = 0L;

    @Override
    public Film create(Film film) {
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
    public Film update(Film film) {
        if (!films.containsKey(film.getId())) {
            log.debug("попытка изменить фильм с несуществующим id = {}", film.getId());
            throw new NotFoundResourceException("попытка изменить фильм с несуществующим id = " + film.getId());
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Optional<Film> findFilmById(Long filmId) {
        return Optional.ofNullable(films.get(filmId));
    }

    @Override
    public boolean addLike(Long filmId, Long idUser) {
        return films.get(filmId)
                .getUserLikes()
                .add(idUser);
    }

    @Override
    public boolean removeLike(Long filmId, Long idUser) {
        return films.get(filmId)
                .getUserLikes()
                .remove(idUser);
    }

    @Override
    public List<Film> findMostPopularFilm(Integer count) {
        return films.values().stream()
                .sorted(Comparator.comparing(film -> film.getUserLikes().size(),
                        Comparator.reverseOrder()))
                .limit(count)
                .toList();
    }
}