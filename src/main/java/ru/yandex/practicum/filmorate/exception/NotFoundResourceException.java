package ru.yandex.practicum.filmorate.exception;

public class NotFoundResourceException extends RuntimeException {
    public NotFoundResourceException(String message) {
        super(message);
    }
}