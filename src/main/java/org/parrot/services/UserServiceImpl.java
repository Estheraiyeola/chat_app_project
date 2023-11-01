package org.parrot.services;

import org.parrot.data.model.Chat;
import org.parrot.data.model.Message;
import org.parrot.data.model.User;
import org.parrot.data.repository.UserRepository;
import org.parrot.dto.*;
import org.parrot.exception.MessageNotFoundException;
import org.parrot.exception.MessageOwnershipException;
import org.parrot.exception.UserAlreadyExistsException;
import org.parrot.exception.UserNotFoundException;
import org.parrot.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static org.parrot.util.Mapper.map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatServices chatServices;
    @Autowired
    private MessageServices messageServices;
    @Override
    public User registerUser(RegisterUserRequest registerUserRequest) throws UserAlreadyExistsException {
        User user = null;
        if (!userAlreadyExists(registerUserRequest)){
            user = map(registerUserRequest);
            userRepository.save(user);
        }
        return user;
    }

    private Chat createChat(CreateChatRequest createChatRequest) {
        Chat chat = chatServices.createChat(createChatRequest);
        return chat;
    }

    @Override
    public Message sendMessage(SendMessageRequest sendMessageRequest) {
        List<User> participants = List.of(sendMessageRequest.getFrom(), sendMessageRequest.getTo());
        Chat chat = findChat(participants);
        if (chat == null) {
            CreateChatRequest createChatRequest = new CreateChatRequest();
            createChatRequest.setFrom(sendMessageRequest.getFrom());
            createChatRequest.setTo(sendMessageRequest.getTo());
            chat = createChat(createChatRequest);
        }
        Message message = messageServices.sendMessage(sendMessageRequest, chat);
        message.setFro(sendMessageRequest.getFrom());
        message.setTo(sendMessageRequest.getTo());
        Mapper mapper = new Mapper();
        mapper.map(chat, message);
        return message;
    }

    @Override
    public void deleteMessage(DeleteMessageRequest deleteMessageRequest) throws MessageOwnershipException {
        messageServices.deleteMessage(deleteMessageRequest);
    }

    @Override
    public void deleteAllMessages() {
        messageServices.deleteAllMessages();
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {
        LoginResponse loginResponse = userRepository.findDistinctByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        loginResponse.setSuccess(true);
        loginResponse.setMessage("Successful");
        return loginResponse;
    }

    @Override
    public Message editMessage(EditMessageRequest editMessageRequest) throws MessageNotFoundException {
        return messageServices.editMessage(editMessageRequest);
    }


    private Chat findChat(List<User> participants) {
        return chatServices.findChat(participants);
    }

    private boolean userAlreadyExists(RegisterUserRequest registerUserRequest) throws UserAlreadyExistsException {
        for (User user:userRepository.findAll()) {
            if (user.getEmail().equals(registerUserRequest.getEmail())){
                throw new UserAlreadyExistsException("User Already Exists");
            }
        }
        return false;
    }
    @Override
    public Chat findChatBy(FindChatRequest findChatRequest){
        return chatServices.findChat(findChatRequest.getParticipants());
    }
}
