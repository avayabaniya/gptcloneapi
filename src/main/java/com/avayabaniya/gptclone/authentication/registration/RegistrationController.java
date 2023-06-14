package com.avayabaniya.gptclone.authentication.registration;

import com.avayabaniya.gptclone.authentication.registration.request.RegistrationRequest;
import com.avayabaniya.gptclone.authentication.jwt.response.JwtTokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping("/auth/registration")
    public ResponseEntity<JwtTokenResponse> registration(@RequestBody RegistrationRequest request) {
        JwtTokenResponse response = this.registrationService.registration(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
