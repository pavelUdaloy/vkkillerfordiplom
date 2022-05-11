package by.diplom.killer.vk.service.mapper;

import by.diplom.killer.vk.controller.http.entity.MessageDto;
import by.diplom.killer.vk.controller.http.entity.SendMessageDto;
import by.diplom.killer.vk.controller.http.entity.SendedMessageDto;
import by.diplom.killer.vk.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "chat", ignore = true)
    @Mapping(target = "publicationDate", ignore = true)
    Message mapSendMessageDtoToMessage(SendMessageDto sendMessageDto);

    @Mapping(target = "userId", ignore = true)
    SendedMessageDto mapMessageToSentMessageDto(Message message);

    default SendedMessageDto mapMessageToSentMessageDtoWithUserAndChat(Message message) {
        SendedMessageDto sendedMessageDto = mapMessageToSentMessageDto(message);
        sendedMessageDto.setUserId(message.getUser().getId());
        sendedMessageDto.setChatId(message.getChat().getId());
        return sendedMessageDto;
    }

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    MessageDto mapMessageToMessageDto(Message message);

    default MessageDto mapMessageToMessageDtoWithUserAndChat(Message message) {
        MessageDto messageDto = mapMessageToMessageDto(message);
        messageDto.setUserId(message.getUser().getId());
        messageDto.setFirstName(message.getUser().getFirstName());
        messageDto.setLastName(message.getUser().getLastName());
        return messageDto;
    }
}
