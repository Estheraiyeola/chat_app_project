package org.parrot.data.repository;

import org.parrot.data.model.Chat;
import org.parrot.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
    Chat findChatByParticipantsIn(List<User> participants);
}
