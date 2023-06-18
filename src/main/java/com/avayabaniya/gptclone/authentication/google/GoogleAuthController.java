package com.avayabaniya.gptclone.authentication.google;

import com.avayabaniya.gptclone.authentication.google.response.GoogleOAuthTokenResponse;
import com.avayabaniya.gptclone.authentication.jwt.response.JwtTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;

@RestController
public class GoogleAuthController {

    private final GoogleAuthService googleAuthService;

    @Autowired
    public GoogleAuthController(GoogleAuthService googleAuthService) {
        this.googleAuthService = googleAuthService;
    }


    @GetMapping("/login/google")
    public ResponseEntity<Void> redirectToGoogleLogin() {
        HttpHeaders headers = this.googleAuthService.redirectToGoogleLogin();
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


    @GetMapping("/login/oauth2/code/google") //callback from google
    public ResponseEntity<JwtTokenResponse> handleCallback(@RequestParam("code") String code) throws GeneralSecurityException, IOException {
        JwtTokenResponse response = this.googleAuthService.handleCallback(code);
        return  new ResponseEntity<>(response, HttpStatus.OK);
    }
}
