package com.avayabaniya.gptclone.chat.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatRequest {

    private String chatId;

    private String chatMessage;

}
