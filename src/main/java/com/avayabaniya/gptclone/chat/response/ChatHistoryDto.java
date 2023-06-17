package com.avayabaniya.gptclone.chat.response;

import com.avayabaniya.gptclone.chat.model.ChatHistory;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatHistoryDto {

    public String chatId;

    public String title;

    public String sentMessage;

    public String receivedMessage;

    public Date createdAt;

    public ChatHistoryDto(ChatHistory history) {
        this.chatId = history.getChat().getChatId();
        this.title = history.getChat().getTitle();
        this.sentMessage = history.getSentMessage();
        this.receivedMessage = history.getReceivedMessage();
        this.createdAt = history.getCreatedAt();
    }
}
