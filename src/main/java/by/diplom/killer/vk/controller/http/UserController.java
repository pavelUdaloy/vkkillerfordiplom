package by.diplom.killer.vk.controller.http;

import by.diplom.killer.vk.config.JwtProperties;
import by.diplom.killer.vk.controller.http.entity.AuthDto;
import by.diplom.killer.vk.controller.http.entity.AuthedUserDto;
import by.diplom.killer.vk.controller.http.entity.RegisterUserDto;
import by.diplom.killer.vk.controller.http.entity.UserDto;
import by.diplom.killer.vk.exception.BaseKillerException;
import by.diplom.killer.vk.service.AuthService;
import by.diplom.killer.vk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        UserDto userDto = userService.registerUser(registerUserDto);
        if (userDto == null) {
            throw new BaseKillerException("Duplicate email");
        }

        AuthedUserDto authedUserDto = authService.loginUser(AuthDto.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build());

        return ResponseEntity.ok()
                .header(jwtProperties.getTokenHeader(), authedUserDto.getUserTokenDto().getAccessToken())
                .body(authedUserDto.getUserDto());
    }
}
