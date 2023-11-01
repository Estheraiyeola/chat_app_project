package org.parrot.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parrot.data.model.Chat;
import org.parrot.data.model.Message;
import org.parrot.data.model.User;
import org.parrot.data.repository.ChatRepository;
import org.parrot.data.repository.MessageRepository;
import org.parrot.data.repository.UserRepository;
import org.parrot.dto.*;
import org.parrot.exception.MessageOwnershipException;
import org.parrot.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private MessageRepository messageRepository;
    @BeforeEach
    public void setUp(){
        userRepository.deleteAll();
        chatRepository.deleteAll();
        messageRepository.deleteAll();
    }

    @Test
    public void testThatUserCanRegister() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));
    }
    @Test
    public void testThatUserCanRegister_And_Login() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserName(registerUserRequest.getUserName());
        loginRequest.setEmail(registerUserRequest.getEmail());
        loginRequest.setPassword(registerUserRequest.getPassword());
        LoginResponse loginResponse = userService.loginUser(loginRequest);

        assertThat(loginResponse.isSuccess(), is(true));
    }

    @Test
    public void testThatUserCanRegister_Only_Once() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("esther");
        registerUserRequest2.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(registerUserRequest2));
        assertThat(userRepository.count(), is(1L));
    }
    @Test
    public void testThat_Two_UsersCanRegister() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));
    }

//    @Test
//    public void testThat_A_UserCan_CreateChat() throws UserAlreadyExistsException {
//        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
//        registerUserRequest.setUserName("esther");
//        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
//        registerUserRequest.setPassword("password");
//        User user1 = userService.registerUser(registerUserRequest);
//        assertThat(userRepository.count(), is(1L));
//
//        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
//        registerUserRequest2.setUserName("temitope");
//        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
//        registerUserRequest2.setPassword("password");
//        User user2 = userService.registerUser(registerUserRequest2);
//        assertThat(userRepository.count(), is(2L));
//
//        CreateChatRequest createChatRequest = new CreateChatRequest();
//        createChatRequest.setFrom(user1);
//        createChatRequest.setTo(user2);
//        Chat chat = userService.createChat(createChatRequest);
//        assertThat(chatRepository.count(), is(1L));
//    }
    @Test
    public void testThat_User_CanSendMessage() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        User user1 = userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        User user2 = userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));

        Message message = new Message();
        message.setBody("I am coding");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom(user1);
        sendMessageRequest.setTo(user2);
        sendMessageRequest.setMessage(message);
        Message sentMessage = userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((1L)));

    }

    @Test
    public void testThat_User_CanSendMessage_Twice_And_Still_Have_One_Chat() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        User user1 = userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        User user2 = userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));

        Message message = new Message();
        message.setBody("I am coding");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom(user1);
        sendMessageRequest.setTo(user2);
        sendMessageRequest.setMessage(message);
        Message sentMessage = userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((1L)));

        Message message2 = new Message();
        message2.setBody("I am playing");

        SendMessageRequest sendMessageRequest2 = new SendMessageRequest();
        sendMessageRequest2.setFrom(user2);
        sendMessageRequest2.setTo(user1);
        sendMessageRequest2.setMessage(message2);
        Message sentMessage2 = userService.sendMessage(sendMessageRequest2);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((2L)));

    }

    @Test
    public void testThat_User_CanDelete_A_Message() throws UserAlreadyExistsException, MessageOwnershipException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        User user1 = userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        User user2 = userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));

        Message message = new Message();
        message.setBody("I am coding");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom(user1);
        sendMessageRequest.setTo(user2);
        sendMessageRequest.setMessage(message);
        Message sentMessage = userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((1L)));

        Message message2 = new Message();
        message2.setBody("I am playing");

        SendMessageRequest sendMessageRequest2 = new SendMessageRequest();
        sendMessageRequest2.setFrom(user2);
        sendMessageRequest2.setTo(user1);
        sendMessageRequest2.setMessage(message2);
        Message sentMessage2 = userService.sendMessage(sendMessageRequest2);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((2L)));

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setChatId(sentMessage2.getChatId());
        deleteMessageRequest.setMessageId(sentMessage2.getId());
        deleteMessageRequest.setUser(user2);
        userService.deleteMessage(deleteMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(1L));

    }
    @Test
    public void testThat_User_CanDelete_All_Messages() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        User user1 = userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        User user2 = userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));

        Message message = new Message();
        message.setBody("I am coding");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom(user1);
        sendMessageRequest.setTo(user2);
        sendMessageRequest.setMessage(message);
        Message sentMessage = userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((1L)));

        Message message2 = new Message();
        message2.setBody("I am playing");

        SendMessageRequest sendMessageRequest2 = new SendMessageRequest();
        sendMessageRequest2.setFrom(user2);
        sendMessageRequest2.setTo(user1);
        sendMessageRequest2.setMessage(message2);
        Message sentMessage2 = userService.sendMessage(sendMessageRequest2);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((2L)));

        userService.deleteAllMessages();

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(0L));

    }

    @Test
    public void testThat_User_Cannot_Delete_A_Message_They_Did_Not_Send() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        User user1 = userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        User user2 = userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));

        Message message = new Message();
        message.setBody("I am coding");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom(user1);
        sendMessageRequest.setTo(user2);
        sendMessageRequest.setMessage(message);
        Message sentMessage = userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((1L)));

        Message message2 = new Message();
        message2.setBody("I am playing");

        SendMessageRequest sendMessageRequest2 = new SendMessageRequest();
        sendMessageRequest2.setFrom(user2);
        sendMessageRequest2.setTo(user1);
        sendMessageRequest2.setMessage(message2);
        Message sentMessage2 = userService.sendMessage(sendMessageRequest2);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((2L)));

        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest();
        deleteMessageRequest.setChatId(sentMessage2.getChatId());
        deleteMessageRequest.setMessageId(sentMessage2.getId());
        deleteMessageRequest.setUser(user1);
        assertThrows(MessageOwnershipException.class, () -> userService.deleteMessage(deleteMessageRequest));

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is(2L));

    }
    @Test
    public void testThat_User_Can_Edit_Messages_Sent_By_Them() throws UserAlreadyExistsException {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest();
        registerUserRequest.setUserName("esther");
        registerUserRequest.setEmail("estheraiyeola@yahoo.com");
        registerUserRequest.setPassword("password");
        User user1 = userService.registerUser(registerUserRequest);
        assertThat(userRepository.count(), is(1L));

        RegisterUserRequest registerUserRequest2 = new RegisterUserRequest();
        registerUserRequest2.setUserName("temitope");
        registerUserRequest2.setEmail("temitopeaiyeola@yahoo.com");
        registerUserRequest2.setPassword("password");
        User user2 = userService.registerUser(registerUserRequest2);
        assertThat(userRepository.count(), is(2L));

        Message message = new Message();
        message.setBody("I am coding");

        SendMessageRequest sendMessageRequest = new SendMessageRequest();
        sendMessageRequest.setFrom(user1);
        sendMessageRequest.setTo(user2);
        sendMessageRequest.setMessage(message);
        Message sentMessage = userService.sendMessage(sendMessageRequest);

        assertThat(chatRepository.count(), is(1L));
        assertThat(messageRepository.count(), is((1L)));

        EditMessageRequest editMessageRequest = new EditMessageRequest();
        editMessageRequest.setMessageId(sentMessage.getId());
        editMessageRequest.setChatId(sentMessage.getChatId());
        editMessageRequest.setMessageBody("Just made a correction");
        Message editedMessage = userService.editMessage(editMessageRequest);

        assertThat(editedMessage.getBody(), is("Just made a correction"));
        assertThat(messageRepository.count(), is(1L));
        assertThat(chatRepository.count(), is(1L));

    }

}