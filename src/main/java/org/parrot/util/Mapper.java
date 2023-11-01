package org.parrot.util;

import org.parrot.data.model.Chat;
import org.parrot.data.model.Message;
import org.parrot.data.model.User;
import org.parrot.data.repository.ChatRepository;
import org.parrot.dto.CreateChatRequest;
import org.parrot.dto.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {
    @Autowired
    private ChatRepository chatRepository;

    public static User map(RegisterUserRequest registerUserRequest){
        User user = new User();
        user.setUserName(registerUserRequest.getUserName());
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());
        user.setDateRegistered(registerUserRequest.getDateCreated());
        return user;
    }

    public static Chat map(CreateChatRequest createChatRequest) {
        Chat chat = new Chat();
        chat.setParticipants(List.of(createChatRequest.getFrom(),createChatRequest.getTo()));
        chat.setChatName(createChatRequest.getFrom().getUserName() + " and "
                + createChatRequest.getTo().getUserName()
                + " Chat");
        return chat;
    }

    public void map(Chat chat, Message message) {
        chat.getMessage().add(message);
    }
}
