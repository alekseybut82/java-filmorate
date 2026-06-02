package ru.yandex.practicum.filmorate.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDto {

    private final String error;

    private final String description;

}

