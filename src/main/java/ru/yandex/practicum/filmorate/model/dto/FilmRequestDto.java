package ru.yandex.practicum.filmorate.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class FilmRequestDto {
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
