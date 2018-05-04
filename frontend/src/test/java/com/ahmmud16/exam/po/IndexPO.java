package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.stream.Collectors;

public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Homepage");
    }

    public BookDetailsPO chooseBookJavaEE() {
        BookDetailsPO bookDetailsPO = new BookDetailsPO(this);
        getDriver().findElement(By.xpath("//A[@id='bookTable:0:goToDetailsId']")).click();
        return bookDetailsPO;
    }

    public void clickAddUserToListButton() {
        getDriver().findElement(By.xpath("//INPUT[@id='bookTable:0:addUserToListId']")).click();
    }

    public void clickRemoveUserFromListButton() {
        getDriver().findElement(By.xpath("//INPUT[@id='bookTable:0:removeUserFromListId']")).click();
    }

    public String checkCountOfSellers(String id) {
        return getDriver().findElement(By.xpath("//LABEL[@id='bookTable:"+id+":sellerId']")).getText();
    }

}
