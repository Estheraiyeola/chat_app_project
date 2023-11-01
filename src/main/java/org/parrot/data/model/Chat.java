package org.parrot.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("Chats")
public class Chat {
    @Id
    private String id;
    @DBRef
    private List<User> participants;
    @DBRef
    private List<Message> message = new ArrayList<>();
    private String chatName;
}
