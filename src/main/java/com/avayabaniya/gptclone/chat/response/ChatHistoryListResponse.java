package com.avayabaniya.gptclone.chat.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ChatHistoryListResponse {

    private List<ChatHistoryDto> data;

    private int count;
}
