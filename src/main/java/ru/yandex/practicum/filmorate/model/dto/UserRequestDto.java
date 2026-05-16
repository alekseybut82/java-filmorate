package ru.yandex.practicum.filmorate.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;

@NoArgsConstructor
@Setter
@Getter
public class UserRequestDto {

    private Long id;

    @Email(message = "электронная почта должна быть сформирована правильно")
    private String email;

    @NotBlank(message = "логин не может быть пустым или состоять из пробелов")
    private String login;

    private String name;

    @PastOrPresent(message = "дата не может быть в будущем")
    private LocalDate birthday;
}
