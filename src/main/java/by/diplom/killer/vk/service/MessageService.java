package by.diplom.killer.vk.service;

import by.diplom.killer.vk.controller.http.entity.MessageDto;
import by.diplom.killer.vk.controller.http.entity.SendMessageDto;
import by.diplom.killer.vk.controller.http.entity.SendedMessageDto;
import by.diplom.killer.vk.entity.Chat;
import by.diplom.killer.vk.entity.Message;
import by.diplom.killer.vk.entity.User;
import by.diplom.killer.vk.repository.MessageRepository;
import by.diplom.killer.vk.service.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;

    @Transactional
    public SendedMessageDto sendMessage(Long chatId, SendMessageDto sendMessageDto) {
        Chat chat = chatService.getChatById(chatId);
        User user = userService.getUserById(sendMessageDto.getUserId());

        Message message = messageMapper.mapSendMessageDtoToMessage(sendMessageDto);
        message.setChat(chat);
        message.setUser(user);
        message.setPublicationDate(LocalDateTime.now());

        messageRepository.save(message);

        return messageMapper.mapMessageToSentMessageDtoWithUserAndChat(message);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getMessages(Long chatId, int size, int page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages = messageRepository.findAllByChatIdAndOrderByPublicationDateDesc(chatId, pageable);

        List<MessageDto> messageDtoList = new ArrayList<>();
        for (Message message : messages) {
            messageDtoList.add(messageMapper.mapMessageToMessageDtoWithUserAndChat(message));
        }
        return new PageImpl<>(messageDtoList);
    }
}
