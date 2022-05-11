package by.diplom.killer.vk.controller.http;

import by.diplom.killer.vk.config.JwtProperties;
import by.diplom.killer.vk.controller.http.entity.AuthDto;
import by.diplom.killer.vk.controller.http.entity.AuthedUserDto;
import by.diplom.killer.vk.controller.http.entity.UserDto;
import by.diplom.killer.vk.controller.http.entity.UserTokenDto;
import by.diplom.killer.vk.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private static final String REFRESH_TOKEN_HEADER = "Authorization-Refresh";

    private final JwtProperties jwtProperties;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody AuthDto authDto) {
        AuthedUserDto authedUserDto = authService.loginUser(authDto);
        return ResponseEntity.ok()
                .header(jwtProperties.getTokenHeader(), authedUserDto.getUserTokenDto().getAccessToken())
                .body(authedUserDto.getUserDto());
    }

    @PostMapping("/logout")
    public void logoutUser(@RequestHeader(AUTHORIZATION) String accessToken) {
        authService.logoutUser(accessToken);
    }

    @PostMapping("/access")
    public UserTokenDto refreshTokens(@RequestHeader(AUTHORIZATION) String accessToken) {
        return authService.refreshTokensByAccess(accessToken);
    }

    @PostMapping("/refresh")
    public AuthedUserDto reLoginUser(@RequestHeader(REFRESH_TOKEN_HEADER) String refreshToken) {
        return authService.reLoginUser(refreshToken);
    }
}
