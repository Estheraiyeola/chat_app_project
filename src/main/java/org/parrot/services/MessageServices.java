package org.parrot.services;

import org.parrot.data.model.Chat;
import org.parrot.data.model.Message;
import org.parrot.dto.DeleteMessageRequest;
import org.parrot.dto.EditMessageRequest;
import org.parrot.dto.SendMessageRequest;

public interface MessageServices {
    Message sendMessage(SendMessageRequest sendMessageRequest, Chat chat);

    void deleteMessage(DeleteMessageRequest deleteMessageRequest);

    void deleteAllMessages();

    Message editMessage(EditMessageRequest editMessageRequest);
}
