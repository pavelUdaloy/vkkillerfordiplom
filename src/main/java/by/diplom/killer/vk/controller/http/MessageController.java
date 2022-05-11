package by.diplom.killer.vk.controller.http;

import by.diplom.killer.vk.controller.http.entity.GetMessagesInChatResponseDto;
import by.diplom.killer.vk.controller.http.entity.SendMessageDto;
import by.diplom.killer.vk.controller.http.entity.SendedMessageDto;
import by.diplom.killer.vk.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/chat/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public SendedMessageDto sendMessage(
            @PathVariable("id") Long chatId,
            @RequestBody SendMessageDto sendMessageDto) {

        return messageService.sendMessage(chatId, sendMessageDto);
    }

    @GetMapping
    public GetMessagesInChatResponseDto getMessages(
            @PathVariable("id") Long chatId,
            @RequestParam("size") int size,
            @RequestParam("page") int page) {

        return GetMessagesInChatResponseDto.builder()
                .chatId(chatId)
                .messages(messageService.getMessages(chatId, size, page))
                .build();
    }
}
