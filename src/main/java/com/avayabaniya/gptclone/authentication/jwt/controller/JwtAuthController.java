package com.avayabaniya.gptclone.authentication.jwt.controller;

import com.avayabaniya.gptclone.authentication.jwt.request.JwtTokenRequest;
import com.avayabaniya.gptclone.authentication.jwt.response.JwtTokenResponse;
import com.avayabaniya.gptclone.authentication.jwt.service.JwtService;
import com.avayabaniya.gptclone.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtAuthController {

    private static final Logger logger
            = LoggerFactory.getLogger(JwtAuthController.class);

    private final JwtService jwtService;

    @Autowired
    public JwtAuthController (JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @PostMapping("/auth/token")
    public ResponseEntity<JwtTokenResponse> token(@RequestBody JwtTokenRequest request) throws UserNotFoundException {
        logger.info("INFO: JwtAuthController/token request {}", request);
        JwtTokenResponse response = this.jwtService.generateTokens(request);
        logger.info("RESPONSE: JwtAuthController/token response {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
