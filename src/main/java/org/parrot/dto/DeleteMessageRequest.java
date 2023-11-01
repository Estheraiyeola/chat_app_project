package org.parrot.dto;

import lombok.Data;
import org.parrot.data.model.User;

@Data
public class DeleteMessageRequest {
    private String chatId;
    private String messageId;
    private User user;

}
