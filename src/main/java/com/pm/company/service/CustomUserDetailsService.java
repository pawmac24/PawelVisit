package com.pm.company.service;

import com.pm.company.model.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by pmackiewicz on 2015-12-07.
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    public final static Logger logger = Logger.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user = userService.findByUsername(username);
            if(user == null) {
                throw new UsernameNotFoundException("UserName "+username+" not found");
            }
            return new SecurityUser(user);
    }
}
