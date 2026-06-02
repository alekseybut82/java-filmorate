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

        LocalDate firstReleaseDate = appConfig.getFirstReleaseDate();

        if (film.getReleaseDate().isBefore(firstReleaseDate)) {
            log.info("Валидация не пройдена: дата релиза {}  раньше {}", film.getReleaseDate(), firstReleaseDate);
            throw new ValidationException("дата релиза — не раньше " + firstReleaseDate);
        }
        return film;
    }

    public void addLike(String id, String userId) {

        var filmId = transformID(id);
        var idUser = userService.transformID(userId);

        validateFilmAndUserExists(filmId, idUser);

        filmStorage.addLike(filmId, idUser);
    }

    public void removeLike(String id, String userId) {

        var filmId = transformID(id);
        var idUser = userService.transformID(userId);

        validateFilmAndUserExists(filmId, idUser);

        if (filmStorage.removeLike(filmId, idUser)) {
            log.warn("У фильма id {} нет like от пользователя {}, операция like не выполнялась",
                    filmId, idUser);
        }
    }

    public List<FilmResponseDto> findMostPopularFilm(String count) {
        Integer countInt = Integer.parseInt(count);
        if (countInt < 1) {
            throw new ValidationException("Количество фильмов в списке не может быть меньше 1: " + countInt);
        }

        return filmStorage.findMostPopularFilm(countInt).stream()
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

    public Long transformID(String id) {
        try {
            return Long.valueOf(id);
        } catch (NumberFormatException e) {
            throw new ValidationException("Некорректный формат Id " + id);
        }
    }

}
