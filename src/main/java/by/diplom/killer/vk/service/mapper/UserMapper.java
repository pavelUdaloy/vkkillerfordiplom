package by.diplom.killer.vk.service.mapper;

import by.diplom.killer.vk.controller.http.entity.RegisterUserDto;
import by.diplom.killer.vk.controller.http.entity.UserDto;
import by.diplom.killer.vk.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email",  source = "email")
    User mapRegisterUserDtoToUser(RegisterUserDto registerUserDto);

    @Mapping(target = "email",  source = "email")
    UserDto mapUserToRegisteredUserDto(User user);

    @Mapping(target = "email", source = "email")
    UserDto mapUserToUserDto(User user);
}
