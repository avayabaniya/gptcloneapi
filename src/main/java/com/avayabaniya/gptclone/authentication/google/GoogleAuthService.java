package com.avayabaniya.gptclone.authentication.google;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.authentication.AuthUserDetails;
import com.avayabaniya.gptclone.authentication.google.response.GoogleOAuthTokenResponse;
import com.avayabaniya.gptclone.authentication.jwt.JwtUtils;
import com.avayabaniya.gptclone.authentication.jwt.request.JwtTokenRequest;
import com.avayabaniya.gptclone.authentication.jwt.response.JwtTokenResponse;
import com.avayabaniya.gptclone.authentication.registration.RegistrationDao;
import com.avayabaniya.gptclone.authentication.registration.request.RegistrationRequest;
import com.avayabaniya.gptclone.config.AppPropertiesConfig;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleAuthService {

    private final RestTemplate restTemplate;

    private final AppPropertiesConfig propertiesConfig;

    private final RegistrationDao registrationDao;

    private final JwtUtils jwtUtils;

    public GoogleAuthService(RestTemplateBuilder restTemplateBuilder, AppPropertiesConfig propertiesConfig, RegistrationDao registrationDao, JwtUtils jwtUtils) {
        this.restTemplate = restTemplateBuilder.build();
        this.propertiesConfig = propertiesConfig;
        this.registrationDao = registrationDao;
        this.jwtUtils = jwtUtils;
    }

    public HttpHeaders redirectToGoogleLogin() {
        String authUrl = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=112279668648-9heiot044hmqg718nolbrm328hbvtj50.apps.googleusercontent.com" +
                "&redirect_uri=" + this.propertiesConfig.getGoogleCallbackUrl() +
                "&response_type=code" +
                "&scope=openid%20email%20profile";

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(authUrl));
        return headers;
    }

    public JwtTokenResponse handleCallback(String code) throws GeneralSecurityException, IOException {
        String tokenEndpoint = "https://oauth2.googleapis.com/token";
        String redirectUri = this.propertiesConfig.getGoogleCallbackUrl();
        String clientId = this.propertiesConfig.getGoogleClientId();
        String clientSecret = this.propertiesConfig.getGoogleClientSecret();

        // Set the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

       /* MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("grant_type", "authorization_code");


        // Create the request entity
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        System.out.println(requestEntity);



        // Send the token request
        ResponseEntity<GoogleOAuthTokenResponse> responseEntity = restTemplate.exchange(tokenEndpoint, HttpMethod.POST, requestEntity, GoogleOAuthTokenResponse.class);
        GoogleOAuthTokenResponse tokenResponse = responseEntity.getBody();
        System.out.println(tokenResponse);
*/
        // Use the access token to retrieve user information
        String userInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";

        //assert tokenResponse != null;
        headers.setBearerAuth(code);

        // Create a new request entity with the updated headers
        HttpEntity<Void> userInfoRequestEntity = new HttpEntity<>(headers);

        // Send the user info request
        ResponseEntity<GoogleUserInfo> userInfoResponseEntity = restTemplate.exchange(userInfoEndpoint, HttpMethod.GET, userInfoRequestEntity, GoogleUserInfo.class);
        GoogleUserInfo userInfo = userInfoResponseEntity.getBody();
        assert userInfo != null;


        //get api user
        ApiUser apiUser = this.registrationDao.getApiUser(userInfo.getEmail())
                .orElse(new ApiUser());

        apiUser = this.registrationDao.createUser(new RegistrationRequest(userInfo), apiUser, "GOOGLE");

        AuthUserDetails userDetails = new AuthUserDetails(apiUser);
        String token = this.jwtUtils.generateTokenFromUserDetails(userDetails);

        return new JwtTokenResponse(token);
    }

}
