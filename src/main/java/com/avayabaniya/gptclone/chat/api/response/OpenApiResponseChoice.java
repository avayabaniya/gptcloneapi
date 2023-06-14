package com.avayabaniya.gptclone.chat.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenApiResponseChoice {

    private OpenApiResponseChoiceMessage message;

    private String finish_reason;

    private String index;
}
