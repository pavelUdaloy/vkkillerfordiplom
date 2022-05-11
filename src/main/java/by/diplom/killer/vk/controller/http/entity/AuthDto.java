package by.diplom.killer.vk.controller.http.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthDto {

    private String email;
    private String password;
}
