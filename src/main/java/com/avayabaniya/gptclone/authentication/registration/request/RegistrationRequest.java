package com.avayabaniya.gptclone.authentication.registration.request;

import com.avayabaniya.gptclone.authentication.google.GoogleUserInfo;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RegistrationRequest {

    private String username;

    private String email;

    private String password;

    private String picture;

    private String name;

    public RegistrationRequest(GoogleUserInfo googleUserInfo) {
        this.username = googleUserInfo.getEmail();
        this.email = googleUserInfo.getEmail();
        this.picture = googleUserInfo.getPicture();
        this.name = googleUserInfo.getGivenName() + " " + googleUserInfo.getFamilyName();
    }
}
