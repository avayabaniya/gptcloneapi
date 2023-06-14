package com.avayabaniya.gptclone.chat.api.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenApiRequestMessage {

    private String role;

    private String content;
}
