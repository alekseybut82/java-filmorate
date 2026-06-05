package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.config.AppConfig;
import ru.yandex.practicum.filmorate.exception.NotFoundResourceException;
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
    private final UserService userService;
    private final AppConfig appConfig;

    public FilmResponseDto create(FilmRequestDto filmRequestDto) {
        Film filmRequest = filmMapper.toFilm(filmRequestDto);
        validateReleaseDate(filmRequest);
        Film filmResponse = filmStorage.create(filmRequest);
        return filmMapper.toFilmResponseDto(filmResponse);
    }

    public List<FilmResponseDto> getAll() {
        return filmStorage.getAll().stream()
                .map(filmMapper::toFilmResponseDto)
                .toList();
    }

    public FilmResponseDto update(FilmRequestDto filmRequestDto) {
        Film filmRequest = filmMapper.toFilm(filmRequestDto);
        validateReleaseDate(filmRequest);
        Film filmResponse = filmStorage.update(filmRequest);
        return filmMapper.toFilmResponseDto(filmResponse);
    }

    private Film validateReleaseDate(Film film) {

        LocalDate firstReleaseDate = appConfig.getFirstReleaseDate();

        if (film.getReleaseDate().isBefore(firstReleaseDate)) {
            log.info("Валидация не пройдена: дата релиза {}  раньше {}", film.getReleaseDate(), firstReleaseDate);
            throw new ValidationException("дата релиза — не раньше " + firstReleaseDate);
        }
        return film;
    }

    public void addLike(Long filmId, Long userId) {
        validateFilmAndUserExists(filmId, userId);
        filmStorage.addLike(filmId, userId);
    }

    public void removeLike(Long filmId, Long userId) {
        validateFilmAndUserExists(filmId, userId);
        if (filmStorage.removeLike(filmId, userId)) {
            log.warn("У фильма id {} нет like от пользователя {}, операция like не выполнялась",
                    filmId, userId);
        }
    }

    public List<FilmResponseDto> findMostPopularFilm(int count) {
        return filmStorage.findMostPopularFilm(count).stream()
                .map(filmMapper::toFilmResponseDto)
                .toList();
    }

    private void validateFilmAndUserExists(Long filmId, Long userId) {
        filmStorage.findFilmById(filmId)
                .orElseThrow(() -> new NotFoundResourceException("Фильм Id " + filmId + "не найден"));

        if (userService.isUserAbsent(userId)) {
            throw new NotFoundResourceException("Пользователь id = " + userId + " не найден");
        }
    }

}
