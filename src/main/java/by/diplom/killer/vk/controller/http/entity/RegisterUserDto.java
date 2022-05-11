package by.diplom.killer.vk.controller.http.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RegisterUserDto {

    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
