package com.ahmmud16.exam.controller;

import com.ahmmud16.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@RequestScoped
public class SignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;


    private String username;
    private String password;
    private String retypePassword;

    public String signUpUser() {

        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial = special.matcher(username);

        boolean registered = false;

        if (!retypePassword.equals(password)) {
            return "/signup.jsf?faces-redirect=true&mismatchpassworderror=true";
        } else if (hasSpecial.find()) {
            return "/signup.jsf?faces-redirect=true&usernameinvaliderror=true";
        } else {
            try {
                registered = userService.createUser(username, password);
            } catch (Exception e) {
                //nothing to do
            }

            if (registered) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        password,
                        userDetails.getAuthorities());

                authenticationManager.authenticate(token);

                if (token.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(token);
                }

                return "/index.jsf?faces-redirect=true";
            } else {
                return "/signup.jsf?faces-redirect=true&error=true";
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetypePassword() {
        return retypePassword;
    }

    public void setRetypePassword(String retypePassword) {
        this.retypePassword = retypePassword;
    }
}
