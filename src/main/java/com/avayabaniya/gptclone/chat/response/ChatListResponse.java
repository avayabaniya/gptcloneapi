package com.avayabaniya.gptclone.chat.response;

import com.avayabaniya.gptclone.chat.model.Chat;
import lombok.*;

import java.util.Date;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatListResponse {

    private String chatId;

    private String title;

    private Date createdAt;

    public ChatListResponse(Chat chat) {
        this.chatId = chat.getChatId();
        this.title = chat.getTitle();
        this.createdAt = chat.getCreatedAt();
    }

}
