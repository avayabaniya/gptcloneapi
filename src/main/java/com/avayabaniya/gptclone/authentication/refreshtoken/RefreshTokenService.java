package com.avayabaniya.gptclone.authentication.refreshtoken;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.authentication.ApiUserRepository;
import com.avayabaniya.gptclone.authentication.refreshtoken.model.RefreshToken;
import com.avayabaniya.gptclone.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.avayabaniya.gptclone.config.AppPropertiesConfig;
import com.avayabaniya.gptclone.exceptions.InvalidException;
import com.avayabaniya.gptclone.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private Long refreshTokenDurationMs;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private ApiUserRepository apiUserRepository;

    @Autowired
    private AppPropertiesConfig config;

    public Optional<RefreshToken> findByUsername(String username) {
        return this.refreshTokenRepository.findByToken(username);
    }

    public Optional<RefreshToken> findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(String username) throws UserNotFoundException {

        //this.refreshTokenDurationMs =this.config.getRefreshTokenDurationMs();
        //if (this.refreshTokenDurationMs == null) {
            this.refreshTokenDurationMs = 43200000L; //12 hrs
        //}

        ApiUser user = this.apiUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username)); //TODO: EXCEPTION

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setApiUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(this.refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return this.refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new InvalidException("Refresh token was expired. Please make a new token request");
            //TODO: EXCEPTION
        }

        return token;
    }

    public int deleteByUsername(String username) throws UserNotFoundException {
        ApiUser user = this.apiUserRepository.findByUsername(username)
                .orElseThrow(()->new UserNotFoundException(username)); //TODO: EXCEPTION

        return this.refreshTokenRepository.deleteByApiUser(user);
    }
}
