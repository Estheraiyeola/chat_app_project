package org.parrot.dto;

import lombok.Data;
import org.parrot.data.model.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class FindChatRequest {
    @DBRef
    private List<User> participants;
}
