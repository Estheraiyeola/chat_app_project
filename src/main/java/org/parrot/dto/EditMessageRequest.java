package org.parrot.dto;

import lombok.Data;

@Data
public class EditMessageRequest {
    private String messageId;
    private String chatId;
    private String messageBody;
}
