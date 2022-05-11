package by.diplom.killer.vk.controller.http.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageDto {

    private Long id;
    private String text;
    private LocalDateTime publicationDate;
    private Long userId;
    private String firstName;
    private String lastName;
}
