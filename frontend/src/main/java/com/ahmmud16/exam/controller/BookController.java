package com.ahmmud16.exam.controller;

import com.ahmmud16.exam.entity.Book;
import com.ahmmud16.exam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class BookController {

    @Autowired
    private BookService bookService;

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public String addUserToBook(String username, String title) {
        bookService.addOrRemoveUserFromBookList(username, title, true);
        return "index.jsf";
    }

    public String removeUserFromBook(String username, String title) {
        bookService.addOrRemoveUserFromBookList(username, title, false);
        return "index.jsf";
    }

    public boolean isUserInBookList(String username, String title) {
       return bookService.checkIfUserExistsInTheBookList(username, title);
    }

}
