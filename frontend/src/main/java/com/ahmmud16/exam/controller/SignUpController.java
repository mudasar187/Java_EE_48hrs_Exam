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
    private String firstname;
    private String lastname;
    private String password;
    private String retypePassword;
    private boolean isAdmin;

    public String signUpUser() {

//        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
//        Matcher hasSpecial = special.matcher(username);

        boolean registered = false;

        if (!retypePassword.equals(password)) {
            return "/signup.jsf?faces-redirect=true&mismatchpassworderror=true";
        } else {
            try {
                registered = userService.createUser(username, firstname, lastname, password, isAdmin);
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
