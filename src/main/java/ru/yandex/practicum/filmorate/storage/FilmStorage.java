package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import ru.yandex.practicum.filmorate.model.domain.Film;

import java.util.List;

public interface FilmStorage {

    public Film create(@Valid Film film);

    public List<Film> getAll();

    public Film update(@Valid Film film);
}
