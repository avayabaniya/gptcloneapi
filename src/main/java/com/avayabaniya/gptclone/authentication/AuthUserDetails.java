package com.avayabaniya.gptclone.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {

    private final ApiUser apiUser;

    public AuthUserDetails(ApiUser apiUser) {
        this.apiUser = apiUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

        list.add(new SimpleGrantedAuthority("ROLE_" + this.apiUser.getRole()));
        return list;
    }

    public ApiUser getApiUser() {
        return this.apiUser;
    }

    public String getEmail() { return  this.apiUser.getEmail(); }

    @Override
    public String getPassword() {
        return this.apiUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.apiUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
