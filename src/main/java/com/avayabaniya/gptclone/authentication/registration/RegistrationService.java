package com.avayabaniya.gptclone.authentication.registration;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.authentication.AuthUserDetails;
import com.avayabaniya.gptclone.authentication.jwt.JwtUtils;
import com.avayabaniya.gptclone.authentication.jwt.request.JwtTokenRequest;
import com.avayabaniya.gptclone.authentication.jwt.response.JwtTokenResponse;
import com.avayabaniya.gptclone.authentication.registration.request.RegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final RegistrationDao registrationDao;

    private final JwtUtils jwtUtils;

    @Autowired
    public RegistrationService(RegistrationDao registrationDao, JwtUtils jwtUtils) {
        this.registrationDao = registrationDao;
        this.jwtUtils = jwtUtils;
    }

    public JwtTokenResponse registration(RegistrationRequest request) {
        ApiUser user = this.registrationDao.createUser(request, new ApiUser(), "LOCAL");

        JwtTokenRequest jwt = new JwtTokenRequest();
        jwt.setGrant_type("password");
        jwt.setUsername(request.getUsername());
        jwt.setPassword(request.getPassword());

        AuthUserDetails userDetails = new AuthUserDetails(user);

        String token = this.jwtUtils.generateTokenFromUserDetails(userDetails);
        return new JwtTokenResponse(token);
    }
}
