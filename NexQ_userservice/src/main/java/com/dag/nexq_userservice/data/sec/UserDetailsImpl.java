package com.dag.nexq_userservice.data.sec;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@Data
public class UserDetailsImpl implements UserDetails {

    private final String id;
    private final String username;
    private final String password;


    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String id, String username, String password,Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
