package com.avayabaniya.gptclone.authentication.jwt.service;

import com.avayabaniya.gptclone.authentication.AuthUserDetails;
import com.avayabaniya.gptclone.authentication.jwt.JwtUtils;
import com.avayabaniya.gptclone.authentication.jwt.request.JwtTokenRequest;
import com.avayabaniya.gptclone.authentication.jwt.response.JwtTokenResponse;
import com.avayabaniya.gptclone.authentication.refreshtoken.RefreshTokenService;
import com.avayabaniya.gptclone.authentication.refreshtoken.model.RefreshToken;
import com.avayabaniya.gptclone.exceptions.InvalidException;
import com.avayabaniya.gptclone.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class JwtService {

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final RefreshTokenService refreshTokenService;

    @Autowired
    public JwtService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    public JwtTokenResponse generateTokens(JwtTokenRequest request) throws UserNotFoundException {

        if (Objects.equals(request.getGrant_type(), "password")) {
            return this.generateAccessToken(request);
        }

        if (request.getUsername() != null && request.getPassword() != null) {
            return this.generateAccessToken(request);
        }

        if (Objects.equals(request.getGrant_type(), "refresh_token")) {
            return this.generateRefreshToken(request);
        }

        throw new InvalidException("Invalid Grant Type"); //TODO: EXCEPTION
    }

    private JwtTokenResponse generateAccessToken(JwtTokenRequest request) throws UserNotFoundException {

        //check if username password is valid
        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        //set this user as currently logged-in user
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //get logged in user details using principal
        AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

        //generate access token
        String jwt = jwtUtils.generateJwtToken(userDetails);

        //generate refresh token
        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(userDetails.getUsername());

        return new JwtTokenResponse(jwt, refreshToken);
    }

    private JwtTokenResponse generateRefreshToken(JwtTokenRequest request) {
        String requestRefreshToken = request.getRefresh_token();
        RefreshToken refreshToken = this.refreshTokenService.findByToken(requestRefreshToken)
                .orElseThrow(() -> new InvalidException("Invalid refresh token")); //TODO: EXCEPTION

        return this.refreshTokenService.findByToken(requestRefreshToken)
                .map(this.refreshTokenService::verifyExpiration)
                .map(RefreshToken::getApiUser)
                .map(apiUser -> {
                    AuthUserDetails userDetails = new AuthUserDetails(apiUser);
                    String token = this.jwtUtils.generateTokenFromUserDetails(userDetails);
                    return new JwtTokenResponse(token, refreshToken);
                })
                .orElseThrow(() -> new InvalidException("Invalid refresh token")); //TODO: EXCEPTION
    }
}
