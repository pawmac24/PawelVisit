package com.pm.company;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pmackiewicz on 2015-12-07.
 */
public class PasswordEncoderTester {

    public static final String RAW_PASSWORD = "password2";

    public static void main(String[] args) {
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        List<String> encodedPasswordList = new ArrayList<>();

        for(int i = 1; i<= 20; i++){
            String encodedRawPassword = standardPasswordEncoder.encode(RAW_PASSWORD);
            System.out.println(encodedRawPassword);
            encodedPasswordList.add(encodedRawPassword);
        }
        for(String encodedPassword : encodedPasswordList) {
            boolean matches = standardPasswordEncoder.matches(RAW_PASSWORD, encodedPassword);
            System.out.println(matches);
        }

        //
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        List<String> bCryptEncodedPasswordList = new ArrayList<>();

        for(int i = 1; i<= 20; i++){
            String encodedRawPassword = bCryptPasswordEncoder.encode(RAW_PASSWORD);
            System.out.println(encodedRawPassword);
            bCryptEncodedPasswordList.add(encodedRawPassword);
        }
        for(String encodedPassword : bCryptEncodedPasswordList) {
            boolean matches = bCryptPasswordEncoder.matches(RAW_PASSWORD, encodedPassword);
            System.out.println(matches);
        }
    }
}
