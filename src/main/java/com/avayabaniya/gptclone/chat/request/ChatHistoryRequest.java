package com.avayabaniya.gptclone.chat.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatHistoryRequest {

    private String chatId;

    private Integer limit;

    private int skip;

}
