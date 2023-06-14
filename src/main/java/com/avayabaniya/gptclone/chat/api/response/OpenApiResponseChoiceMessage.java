package com.avayabaniya.gptclone.chat.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenApiResponseChoiceMessage {

    private String role;

    private String content;
}
