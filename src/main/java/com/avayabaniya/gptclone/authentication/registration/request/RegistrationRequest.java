package com.avayabaniya.gptclone.authentication.registration.request;

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
}
