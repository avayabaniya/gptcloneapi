package com.avayabaniya.gptclone.authentication.jwt.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class JwtTokenRequest {

    private String grant_type;

    private String username;

    private String password;

    private String refresh_token;
}
