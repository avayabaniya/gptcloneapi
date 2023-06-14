package com.avayabaniya.gptclone.authentication.refreshtoken.repository;

import com.avayabaniya.gptclone.authentication.ApiUser;
import com.avayabaniya.gptclone.authentication.refreshtoken.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByApiUser(ApiUser apiUser);
}
