package by.diplom.killer.vk.controller.http.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenDto {

    private String accessToken;
    private String refreshAccessToken;
}
