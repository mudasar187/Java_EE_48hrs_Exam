package com.ahmmud16.exam.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.function.Supplier;

@Service
public class DefaultDataInitializerService {


    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;


    @PostConstruct
    public void initialize(){

        attempt(() -> userService.createUser("foo@mail.com", "foo", "foo", "123", false));
        attempt(() -> userService.createUser("bar@mail.com", "bar", "bar", "123", true));
        attempt(() -> userService.createUser("jhonny@mail.com", "jhonny", "jhonny", "123", false));
        attempt(() -> userService.createUser("bravo@mail.com", "bravo", "bravo", "123", false));
        attempt(() -> bookService.createBook("Java EE", "Andrea Arcuri", "This is a awesome book!", "Java Enterprise Programming 1"));
        attempt(() -> bookService.createBook("Advanced Java for Dummies", "John Wayne", "Good book to read before Enterprise Programming 1", "Advanced Java"));
        attempt(() -> bookService.addOrRemoveUserFromBookList("jhonny@mail.com", "Java EE", true));
        attempt(() -> bookService.addOrRemoveUserFromBookList("bravo@mail.com", "Advanced Java for Dummies", true));

    }


    private  <T> T attempt(Supplier<T> lambda){
        try{
            return lambda.get();
        }catch (Exception e){
            return null;
        }
    }
}
