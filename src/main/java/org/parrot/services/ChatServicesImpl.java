package org.parrot.services;

import org.parrot.data.model.Chat;
import org.parrot.data.model.User;
import org.parrot.data.repository.ChatRepository;
import org.parrot.dto.CreateChatRequest;
import org.parrot.exception.ChatNotFoundException;
import org.parrot.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class ChatServicesImpl implements ChatServices{
    @Autowired
    private ChatRepository chatRepository;
    @Override
    public Chat createChat(CreateChatRequest createChatRequest) {
        Chat chat = Mapper.map(createChatRequest);
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat findChat(List<User> participants) {
        return chatRepository.findChatByParticipantsIn(participants);
    }


}
