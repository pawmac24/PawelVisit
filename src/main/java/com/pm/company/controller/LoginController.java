package com.pm.company.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by pmackiewicz on 2015-11-27.
 */
@RestController
public class LoginController {

    public final static Logger logger = Logger.getLogger(LoginController.class);

    @RequestMapping(value= "/user", method = RequestMethod.GET)
    public Principal user(Principal user) {
        logger.info("user = " + user);
        return user;
    }

//    @RequestMapping(value= "/user", method = RequestMethod.GET)
//    public String user(Authentication authentication) {
//        String msg = "";
//        for (GrantedAuthority authority : authentication.getAuthorities()) {
//            String role = authority.getAuthority();
//            msg+=authentication.getName()+", You have "+ role;
//        }
//        return msg;
//    }
}
