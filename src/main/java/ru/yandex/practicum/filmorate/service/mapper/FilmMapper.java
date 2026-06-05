package ru.yandex.practicum.filmorate.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.domain.Film;
import ru.yandex.practicum.filmorate.model.dto.FilmRequestDto;
import ru.yandex.practicum.filmorate.model.dto.FilmResponseDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilmMapper {

    FilmResponseDto toFilmResponseDto(Film film);

    Film toFilm(FilmRequestDto filmRequestDto);
}
