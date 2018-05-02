package com.ahmmud16.exam.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long bookId;

    @NotBlank
    @Size(max = 128)
    @Column(unique = true)
    private String bookTitle;

    @NotBlank
    @Size(max = 128)
    private String bookAuthor;

    @NotBlank
    @Size(max = 4096)
    private String bookDescription;

    @NotBlank
    @Size(max = 128)
    private String courseWhereBookIsUsed;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> users;

    public Book() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public String getCourseWhereBookIsUsed() {
        return courseWhereBookIsUsed;
    }

    public void setCourseWhereBookIsUsed(String courseWhereBookIsUsed) {
        this.courseWhereBookIsUsed = courseWhereBookIsUsed;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
