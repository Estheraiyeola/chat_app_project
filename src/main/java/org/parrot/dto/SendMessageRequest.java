package org.parrot.dto;

import lombok.Data;
import org.parrot.data.model.Message;
import org.parrot.data.model.User;

@Data
public class SendMessageRequest {
    private User to;
    private User from;
    private Message message;
}
