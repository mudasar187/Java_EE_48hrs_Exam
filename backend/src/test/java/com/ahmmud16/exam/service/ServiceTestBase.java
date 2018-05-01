package com.ahmmud16.exam.service;

import com.ahmmud16.exam.entity.User;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class ServiceTestBase {

    @Autowired
    private ResetService deleteService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntityManager em;


    @Before
    public void cleanDatabase(){
        deleteService.resetDatabase();
    }

    protected boolean createUser(String user, String password, boolean isAdmin){
        return userService.createUser(user,password,isAdmin);
    }

    protected User getUser(String username) {

        return em.find(User.class, username);
    }
}
