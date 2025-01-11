package com.slaxation.moro.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {


   private final UserEntity userEntity;

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    public UserEntity getUser() {
        return userEntity;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Define logic if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Define logic if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Define logic if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Define logic if needed
    }

}
