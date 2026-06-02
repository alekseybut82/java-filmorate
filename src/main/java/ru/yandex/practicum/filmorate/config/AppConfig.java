package ru.yandex.practicum.filmorate.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@Getter
@Setter
@ConfigurationProperties("app.film")
public class AppConfig {
    private LocalDate firstReleaseDate;
}
