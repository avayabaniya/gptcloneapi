package com.avayabaniya.gptclone.authentication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    Optional<ApiUser> findByUsername(String username);

    Optional<ApiUser> findByEmail(String email);

    @Query(value = "select * from api_users where username = ?1 or email = ?1", nativeQuery = true)
    Optional<ApiUser> findByUsernameOrEmail(String username);

}
