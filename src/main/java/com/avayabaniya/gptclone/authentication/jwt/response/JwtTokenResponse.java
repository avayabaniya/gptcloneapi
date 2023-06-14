package com.avayabaniya.gptclone.authentication.jwt.response;

import com.avayabaniya.gptclone.authentication.refreshtoken.model.RefreshToken;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class JwtTokenResponse {

    private String access_token;

    private String token_type;

    private int expires_in;

    private String refresh_token;

    private Date issued;

    private Date expires;

    public JwtTokenResponse(String accessToken, RefreshToken refreshToken) {
        this.access_token = accessToken;
        this.token_type = "Bearer";
        this.expires_in = 900;
        this.refresh_token = refreshToken.getToken();
        this.issued = new Date();
        this.expires = new Date(System.currentTimeMillis() + (this.expires_in * 100L));

    }

    public JwtTokenResponse(String accessToken) {
        this.access_token = accessToken;
        this.token_type = "Bearer";
        this.expires_in = 900;
        this.issued = new Date();
        this.expires = new Date(System.currentTimeMillis() + (this.expires_in * 100L));

    }
}
