package by.diplom.killer.vk.controller.http.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthedUserDto {

    private UserDto userDto;
    private UserTokenDto userTokenDto;
}
