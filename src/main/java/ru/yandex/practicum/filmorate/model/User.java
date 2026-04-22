package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class User {

    private Long id;

    @Email(message = "электронная почта должна быть сформирована правильно")
    private String email;

    @NotBlank(message = "логин не может быть пустым или состоять из пробелов")
    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;
}