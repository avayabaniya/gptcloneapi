package com.avayabaniya.gptclone.exceptions;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GptExceptionHandler {

    private static final Logger logger
            = LoggerFactory.getLogger(GptExceptionHandler.class);

    @ExceptionHandler(value = {InvalidException.class})
    public ResponseEntity<Map<String,String>> handleInvalidException(InvalidException e) {
        String message = e.getMessage();
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Map<String,String>> handleUserNotFoundException(UserNotFoundException e) {
        String username = e.getUsername();
        String response_message = "User Could not be Found for the given Username: " + username;
        Map<String, String> response = new HashMap<String, String>();
        response.put("message", response_message);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
