package by.diplom.killer.vk.controller.http.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateChatRequestDto {

    private String name;
    private List<Long> userIds;
}
