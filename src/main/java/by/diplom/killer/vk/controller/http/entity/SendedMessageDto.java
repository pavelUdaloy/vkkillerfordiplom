package by.diplom.killer.vk.controller.http.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SendedMessageDto {

    private Long id;
    private Long userId;
    private Long chatId;
    private String text;
    private LocalDate publicationDate;
}
