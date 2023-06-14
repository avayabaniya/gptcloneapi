package com.avayabaniya.gptclone.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailService implements UserDetailsService {

    @Autowired
    ApiUserRepository apiUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        ApiUser apiUser = this.apiUserRepository.findByUsernameOrEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username")); // todo: EXCEPTION

        return new AuthUserDetails(apiUser);
    }
}
