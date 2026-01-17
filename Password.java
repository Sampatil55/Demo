package com.hotel.controller;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Password {
    public static void main(String[] args){
        //String encodedPwd = BCrypt.hashpw("testing", BCrypt.gensalt(10));
        //System.out.println(encodedPwd);

        PasswordEncoder e = new BCryptPasswordEncoder();
        System.out.println(e.encode("testing"));
    }
}
