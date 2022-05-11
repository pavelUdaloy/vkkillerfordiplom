package by.diplom.killer.vk.controller.http;

import by.diplom.killer.vk.controller.http.entity.CreateChatRequestDto;
import by.diplom.killer.vk.controller.http.entity.CreateChatResponseDto;
import by.diplom.killer.vk.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chat", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public CreateChatResponseDto createChat(@RequestBody CreateChatRequestDto createChatRequestDto) {
        return chatService.createChat(createChatRequestDto);
    }
}
