package by.diplom.killer.vk.service;

import by.diplom.killer.vk.controller.http.entity.CreateChatRequestDto;
import by.diplom.killer.vk.controller.http.entity.CreateChatResponseDto;
import by.diplom.killer.vk.entity.Chat;
import by.diplom.killer.vk.entity.User;
import by.diplom.killer.vk.exception.NotFoundException;
import by.diplom.killer.vk.repository.ChatRepository;
import by.diplom.killer.vk.service.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMapper chatMapper;
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Transactional
    public CreateChatResponseDto createChat(CreateChatRequestDto createChatRequestDto) {
        Chat chat = chatMapper.mapCreateChatRequestDtoToChat(createChatRequestDto);

        Set<User> users = new HashSet<>();
        for (Long userId : createChatRequestDto.getUserIds()) {
            User userById = userService.getUserById(userId);
            users.add(userById);
        }
        chat.setUsers(users);

        chatRepository.save(chat);
        return chatMapper.mapChatToCreateChatResponseDtoWithUsers(chat);
    }

    @Transactional(readOnly = true)
    public Chat getChatById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isEmpty()) {
            throw new NotFoundException(String.format("Chat with id={%s}", id));
        }
        return chat.get();
    }
}
