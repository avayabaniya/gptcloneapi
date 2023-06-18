package com.avayabaniya.gptclone.authentication.google.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GoogleOAuthCodeRequest {

    @JsonProperty("code")
    private String code;
}
