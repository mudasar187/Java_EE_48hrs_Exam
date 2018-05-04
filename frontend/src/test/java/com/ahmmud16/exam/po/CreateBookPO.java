package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateBookPO extends LayoutPO {


    public CreateBookPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public CreateBookPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Create book");
    }

    public void createBook(String title, String author, String course, String description) {
        setText("createBookForm:bookTitle", title);
        setText("createBookForm:bookAuthor", author);
        setText("createBookForm:bookCourse", course);
        setText("createBookForm:bookDescription", description);

    }

    public void clickCreateBook() {
        getDriver().findElement(By.xpath("//INPUT[@id='createBookForm:createBookButton']")).click();
    }
}
