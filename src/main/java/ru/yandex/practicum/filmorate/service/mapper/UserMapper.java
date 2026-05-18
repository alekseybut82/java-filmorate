package ru.yandex.practicum.filmorate.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.yandex.practicum.filmorate.model.domain.User;
import ru.yandex.practicum.filmorate.model.dto.UserRequestDto;
import ru.yandex.practicum.filmorate.model.dto.UserResponseDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponseDto toUserResponseDto(User user);

    User toUser(UserRequestDto dto);
}
