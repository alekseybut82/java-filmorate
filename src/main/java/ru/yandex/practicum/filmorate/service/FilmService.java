package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.domain.Film;
import ru.yandex.practicum.filmorate.model.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.model.dto.FilmResponseDto;
import ru.yandex.practicum.filmorate.service.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {

    private final FilmMapper filmMapper;

    private final FilmStorage filmStorage;

    private static final LocalDate FIST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    public FilmResponseDto create(FilmRequestDto filmRequestDto) {
        return filmMapper.toFilmResponseDto(filmStorage.create(validateReleaseDate(filmMapper.toFilm(filmRequestDto))));
    }

    public List<FilmResponseDto> getAll() {
        return filmStorage.getAll().stream()
                .map(filmMapper::toFilmResponseDto)
                .toList();
    }

    public FilmResponseDto update(FilmRequestDto filmRequestDto) {
        return filmMapper.toFilmResponseDto(filmStorage.update(validateReleaseDate(filmMapper.toFilm(filmRequestDto))));
    }

    public Film validateReleaseDate(Film film) {

        if (film.getReleaseDate().isBefore(FIST_RELEASE_DATE)) {
            log.info("Вадиация не пройдена: дата релиза {}  раньше 28 декабря 1895 года", film.getReleaseDate());
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
        return film;

    }
}
