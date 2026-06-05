package ru.yandex.practicum.filmorate.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class UserResponseDto {

    private Long id;

    private String email;

    private String login;

    private String name;

    private LocalDate birthday;
}
