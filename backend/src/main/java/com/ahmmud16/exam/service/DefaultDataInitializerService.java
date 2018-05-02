package com.ahmmud16.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {


    @Autowired
    private UserService userService;


    @PostConstruct
    public void initialize(){

        attempt(() -> userService.createUser("foo@mail.com", "foo", "foo", "123", false));
        attempt(() -> userService.createUser("bar@mail.com", "bar", "bar", "123", true));

    }


    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
