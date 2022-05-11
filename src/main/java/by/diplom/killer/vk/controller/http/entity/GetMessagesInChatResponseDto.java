package by.diplom.killer.vk.controller.http.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class GetMessagesInChatResponseDto {

    private Long chatId;
    private Page<MessageDto> messages;
}
