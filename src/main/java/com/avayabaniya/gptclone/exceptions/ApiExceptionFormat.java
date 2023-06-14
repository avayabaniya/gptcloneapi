package com.avayabaniya.gptclone.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiExceptionFormat {
    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private String code;

    private String reference;

    private String error;

    @SerializedName("error_description")
    private String errorDescription;
}
