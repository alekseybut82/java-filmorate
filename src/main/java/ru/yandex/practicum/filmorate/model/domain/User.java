package ru.yandex.practicum.filmorate.model.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    private Long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;

    @Builder.Default
    private Set<Long> friendIds = new HashSet<>();
}