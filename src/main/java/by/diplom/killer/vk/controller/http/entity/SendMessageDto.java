package by.diplom.killer.vk.controller.http.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMessageDto {

    private Long userId;
    private String text;
}
