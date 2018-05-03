package com.ahmmud16.exam.controller;

import com.ahmmud16.exam.entity.Book;
import com.ahmmud16.exam.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Named
@SessionScoped
public class BookController implements Serializable {


    @Autowired
    private BookService bookService;

    private Set<String> sellers;
    private Book book;
    private String formBookTitle;
    private String formBookAuthor;
    private String formBookDescription;
    private String formBookCourse;

    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    public void addUserToBook(String username, String title) {
        bookService.addOrRemoveUserFromBookList(username, title, true);
    }

    public void removeUserFromBook(String username, String title) {
        bookService.addOrRemoveUserFromBookList(username, title, false);
    }

    public boolean isUserInBookList(String username, String title) {
       return bookService.checkIfUserExistsInTheBookList(username, title);
    }

    public Book getBook() {
        return book;
    }

    public Set<String> getSellers() {
        return sellers;
    }

    public String goToDetailPage(String title) {
        book = bookService.getBook(title);
        sellers = book.getUsers();
        return "/bookdetails.jsf?faces-redirect=true";
    }

    public void deleteBook(String title) {
        bookService.deleteBook(title);
    }

    /**
     * Create book start here
     * @return
     */
    private void clearFields() {
        formBookTitle="";
        formBookAuthor="";
        formBookDescription="";
        formBookCourse="";
    }

    public void createBook() {
        bookService.createBook(formBookTitle, formBookAuthor, formBookDescription, formBookCourse);
        clearFields();
    }

    public String getFormBookTitle() {
        return formBookTitle;
    }

    public void setFormBookTitle(String formBookTitle) {
        this.formBookTitle = formBookTitle;
    }

    public String getFormBookAuthor() {
        return formBookAuthor;
    }

    public void setFormBookAuthor(String formBookAuthor) {
        this.formBookAuthor = formBookAuthor;
    }

    public String getFormBookDescription() {
        return formBookDescription;
    }

    public void setFormBookDescription(String formBookDescription) {
        this.formBookDescription = formBookDescription;
    }

    public String getFormBookCourse() {
        return formBookCourse;
    }

    public void setFormBookCourse(String formBookCourse) {
        this.formBookCourse = formBookCourse;
    }
    /**
     * Create book end here
     */
}
