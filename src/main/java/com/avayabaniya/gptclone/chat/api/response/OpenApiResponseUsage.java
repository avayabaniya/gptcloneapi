package com.avayabaniya.gptclone.chat.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class OpenApiResponseUsage {

    private String promo_tokens;

    private String completion_tokens;

    private String total_tokens;
}
