package org.parrot.controller;

import org.parrot.data.model.Chat;
import org.parrot.data.model.User;
import org.parrot.dto.*;
import org.parrot.exception.UserAlreadyExistsException;
import org.parrot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@ModelAttribute RegisterUserRequest registerUserRequest) throws UserAlreadyExistsException {
        User registerUser = userService.registerUser(registerUserRequest);
        return registerUser;
    }
    @PostMapping("/login")
    public LoginResponse authentication(LoginRequest loginRequest){
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        return loginResponse;
    }
    @PostMapping("/parrot/send")
    public String sendMessage(SendMessageRequest sendMessageRequest){
        userService.sendMessage(sendMessageRequest);
        return "Message sent successfully";
    }
    @DeleteMapping("/parrot/delete_one")
    public String deleteMessage(DeleteMessageRequest deleteMessageRequest){
        userService.deleteMessage(deleteMessageRequest);
        return "Message deleted successfully";
    }
    @DeleteMapping("/parrot/delete_all")
    public String clearChat(){
        userService.deleteAllMessages();
        return "Chats cleared";
    }
    @PutMapping("/parrot/edit")
    public String editMessage(EditMessageRequest editMessageRequest){
        userService.editMessage(editMessageRequest);
        return "Successfully Edited";
    }
    @GetMapping("/parrot/find_chat")
    public Chat findChat(FindChatRequest findChatRequest){
        Chat chat = userService.findChatBy(findChatRequest);
        return chat;
    }
}
