package com.avayabaniya.gptclone.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends Exception {
    private String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }
}
