package ru.yandex.practicum.filmorate.model.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;
}