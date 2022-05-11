package by.diplom.killer.vk.service.mapper;

import by.diplom.killer.vk.controller.http.entity.CreateChatRequestDto;
import by.diplom.killer.vk.controller.http.entity.CreateChatResponseDto;
import by.diplom.killer.vk.entity.Chat;
import by.diplom.killer.vk.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "users",  ignore = true)
    Chat mapCreateChatRequestDtoToChat(CreateChatRequestDto registerUserDto);

    @Mapping(target = "userIds",  ignore = true)
    CreateChatResponseDto mapChatToCreateChatResponseDto(Chat chat);

    default CreateChatResponseDto mapChatToCreateChatResponseDtoWithUsers(Chat chat) {
        CreateChatResponseDto createChatResponseDto = mapChatToCreateChatResponseDto(chat);
        List<Long> userIds = new ArrayList<>();
        for (User user : chat.getUsers()) {
            userIds.add(user.getId());
        }
        createChatResponseDto.setUserIds(userIds);
        return createChatResponseDto;
    }
}
