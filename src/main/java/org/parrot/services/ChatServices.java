package org.parrot.services;

import org.parrot.data.model.Chat;
import org.parrot.data.model.User;
import org.parrot.dto.CreateChatRequest;

import java.util.List;

public interface ChatServices {
    Chat createChat(CreateChatRequest createChatRequest);

    Chat findChat(List<User> participants);
}
