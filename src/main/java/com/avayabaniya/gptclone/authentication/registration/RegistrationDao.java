package com.avayabaniya.gptclone.authentication.registration;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.authentication.ApiUserRepository;
import com.avayabaniya.gptclone.authentication.registration.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class RegistrationDao {

    private final PasswordEncoder encoder;

    private final ApiUserRepository apiUserRepository;

    @Autowired
    public RegistrationDao(ApiUserRepository apiUserRepository, PasswordEncoder encoder) {
        this.apiUserRepository = apiUserRepository;
        this.encoder = encoder;
    }

    public ApiUser createUser(RegistrationRequest request, ApiUser apiUser, String authType) {
        apiUser.setUsername(request.getUsername());
        apiUser.setEmail(request.getEmail());
        apiUser.setPassword(encoder.encode(request.getPassword()));
        apiUser.setAuthType(authType);
        apiUser.setRole("USER");

        return this.apiUserRepository.save(apiUser);
    }
}
