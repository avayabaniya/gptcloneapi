package com.avayabaniya.gptclone.chat.response;


import com.avayabaniya.gptclone.chat.model.ChatHistory;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatResponse {

    private String sentMessage;

    private String receivedMessage;

    private String chatId;

    public ChatResponse(ChatHistory chatHistory) {
        this.sentMessage = chatHistory.getSentMessage();
        this.receivedMessage = chatHistory.getReceivedMessage();
        this.chatId = chatHistory.getChat().getChatId();
    }

}
