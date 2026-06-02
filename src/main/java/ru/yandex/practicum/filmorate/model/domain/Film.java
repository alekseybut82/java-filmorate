package ru.yandex.practicum.filmorate.model.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    private Long id;

    private String name;

    private String description;

    private LocalDate releaseDate;

    private Long duration;

    @Builder.Default
    private Set<Long> userLikes = new HashSet<>();

}