package com.avayabaniya.gptclone.chat.api.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenApiChatResponse {

    private String id;

    private String object;

    private String created;

    private String model;

    private OpenApiResponseUsage usage;

    private List<OpenApiResponseChoice> choices;



}
