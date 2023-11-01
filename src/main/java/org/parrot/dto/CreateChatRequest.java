package org.parrot.dto;

import lombok.Data;
import org.parrot.data.model.User;

@Data
public class CreateChatRequest {
    private User from;
    private User to;
}
