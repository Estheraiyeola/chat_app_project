package org.parrot.data.repository;

import org.parrot.data.model.Message;
import org.parrot.dto.DeleteMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MessageRepository extends MongoRepository<Message, String> {
    void deleteById(DeleteMessageRequest deleteMessageRequest);
    Message findDistinctByFroAndChatId(DeleteMessageRequest deleteMessageRequest);
}
