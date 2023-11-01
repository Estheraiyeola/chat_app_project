package org.parrot.services;

import org.parrot.data.model.Chat;
import org.parrot.data.model.Message;
import org.parrot.data.model.User;
import org.parrot.dto.*;
import org.parrot.exception.UserAlreadyExistsException;

public interface UserService {
    User registerUser(RegisterUserRequest registerUserRequest) throws UserAlreadyExistsException;



    Message sendMessage(SendMessageRequest sendMessageRequest);

    void deleteMessage(DeleteMessageRequest deleteMessageRequest);

    void deleteAllMessages();

    LoginResponse loginUser(LoginRequest loginRequest);

    Message editMessage(EditMessageRequest editMessageRequest);

    Chat findChatBy(FindChatRequest findChatRequest);
}
