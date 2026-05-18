package ru.yandex.practicum.filmorate.model.domain;

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

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Long duration;

}