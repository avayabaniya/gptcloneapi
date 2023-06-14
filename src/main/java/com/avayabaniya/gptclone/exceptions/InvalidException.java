package com.avayabaniya.gptclone.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidException extends RuntimeException{
    private String message;
    public InvalidException(String message){
        this.message = message;
    }
}
