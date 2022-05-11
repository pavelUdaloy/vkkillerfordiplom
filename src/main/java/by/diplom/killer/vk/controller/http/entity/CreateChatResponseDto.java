package by.diplom.killer.vk.controller.http.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateChatResponseDto {

    private Long id;
    private String name;
    private List<Long> userIds;
}
