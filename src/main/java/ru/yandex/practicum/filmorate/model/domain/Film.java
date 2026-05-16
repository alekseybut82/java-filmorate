package ru.yandex.practicum.filmorate.model.domain;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * Film.
 */
@Getter
@Setter
@NoArgsConstructor
public class Film {

    private Long id;

    @NotBlank(message = "название фильма не может быть пустым")
    private String name;

    @Size(max = 200, message = "максимальная длина описания — 200 символов")
    private String description;

    @NotNull(message = "дата релиза фильма не должна быть пустой")
    private LocalDate releaseDate;

    @Min(value = 1, message = "продолжительность фильма должна быть положительным числом")
    private Long duration;

}