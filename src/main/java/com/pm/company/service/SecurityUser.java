package com.pm.company.service;

import com.pm.company.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by pmackiewicz on 2015-12-07.
 */
public class SecurityUser extends User implements UserDetails{
    public SecurityUser(User user) {
        if(user != null) {
            this.setUserId(user.getUserId());
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setEmployee(user.getEmployee());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
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
