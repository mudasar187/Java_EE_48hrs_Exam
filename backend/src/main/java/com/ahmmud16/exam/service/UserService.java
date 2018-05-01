package com.ahmmud16.exam.service;

import com.ahmmud16.exam.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;

@Service
@Transactional
public class UserService {

    @Autowired
    private EntityManager em;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public boolean createUser(String username, String password, boolean isAdmin) {
        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        String hashedPassword = passwordEncoder.encode(password);

        if (em.find(User.class, username) != null) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPassword);

        if(isAdmin) {
            user.setRoles(Collections.singleton("ROLE_ADMIN"));
        } else {
            user.setRoles(Collections.singleton("ROLE_USER"));
        }

        user.setEnabled(true);
        em.persist(user);
        return true;
    }
}
