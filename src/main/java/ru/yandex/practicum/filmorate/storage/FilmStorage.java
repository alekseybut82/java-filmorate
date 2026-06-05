package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.domain.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film create(Film film);

    List<Film> getAll();

    Film update(Film film);

    Optional<Film> findFilmById(Long filmId);

    boolean addLike(Long filmId, Long idIuser);

    boolean removeLike(Long filmId, Long idUser);

    List<Film> findMostPopularFilm(Integer countInt);
}
