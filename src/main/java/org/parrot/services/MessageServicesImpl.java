package org.parrot.services;

import org.parrot.data.model.Chat;
import org.parrot.data.model.Message;
import org.parrot.data.repository.MessageRepository;
import org.parrot.dto.DeleteMessageRequest;
import org.parrot.dto.EditMessageRequest;
import org.parrot.dto.SendMessageRequest;
import org.parrot.exception.MessageNotFoundException;
import org.parrot.exception.MessageOwnershipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.parrot.util.Mapper.map;

@Service
public class MessageServicesImpl implements MessageServices{
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(SendMessageRequest sendMessageRequest, Chat chat) {
        Message message = new Message();
        message.setBody(sendMessageRequest.getMessage().getBody());
        message.setDateCreated(sendMessageRequest.getMessage().getDateCreated());
        message.setFro(sendMessageRequest.getFrom());
        message.setTo(sendMessageRequest.getTo());
        message.setChatId(chat.getId());
        messageRepository.save(message);
        return message;
    }

    @Override
    public void deleteMessage(DeleteMessageRequest deleteMessageRequest) throws MessageOwnershipException {
        Optional<Message> messageOptional = messageRepository.findById(deleteMessageRequest.getMessageId());
        if (messageOptional.isPresent()){
            Message message = messageOptional.get();

            if (message.getFro().getEmail().equals(deleteMessageRequest.getUser().getEmail())){
                messageRepository.deleteById(deleteMessageRequest.getMessageId());
            }
            else {
                throw new MessageOwnershipException("Message not owned by you");
            }
        }
    }

    @Override
    public void deleteAllMessages() {
        messageRepository.deleteAll();
    }

    @Override
    public Message editMessage(EditMessageRequest editMessageRequest) throws MessageNotFoundException {
        Optional<Message> messageOptional = messageRepository.findById(editMessageRequest.getMessageId());
        if (messageOptional.isPresent()){
            Message message = messageOptional.get();
            if (message.getChatId().equals(editMessageRequest.getChatId())){
                message.setBody(editMessageRequest.getMessageBody());
                return message;
            }
        }
        throw new MessageNotFoundException("Message not found");
    }
}
