package com.ahmmud16.exam.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.Collection;

@Named
@RequestScoped
public class UserInfoController {


    public String getUserName(){
        return ((UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getUsername();
    }

    /**
     * Get ROLE_ADMIN
     * @return
     */
    public boolean getAdminRoleUser() {
        Collection<? extends GrantedAuthority> role = ((UserDetails)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getAuthorities();
        return role.stream().anyMatch(e -> ((GrantedAuthority) e).getAuthority().contains("ROLE_ADMIN"));
    }

    /**
     * Get ROLE_USER
     */

    public boolean getUserRoleUser() {
        Collection<? extends GrantedAuthority> role = ((UserDetails)SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getAuthorities();
        return role.stream().anyMatch(e -> ((GrantedAuthority) e).getAuthority().contains("ROLE_USER"));
    }
}
