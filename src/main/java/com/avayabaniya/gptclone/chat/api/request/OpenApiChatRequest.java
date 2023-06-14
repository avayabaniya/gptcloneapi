package com.avayabaniya.gptclone.chat.api.request;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenApiChatRequest {

    private String model;

    List<OpenApiRequestMessage> messages;

    public OpenApiChatRequest(List<OpenApiRequestMessage> messages) {
        this.model = "gpt-3.5-turbo";
        this.messages = messages;
    }
}
