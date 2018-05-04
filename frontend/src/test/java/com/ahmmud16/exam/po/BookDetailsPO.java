package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookDetailsPO extends LayoutPO {

    public BookDetailsPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public BookDetailsPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Book details");
    }

    public String getEmailTextFromTable(String mail) {
        return getDriver().findElement(By.xpath("//LABEL[text()='"+mail+"']")).getText();
    }

    public SendMessagePO clickSendMessageButton(String id) {
        SendMessagePO sendMessagePO = new SendMessagePO(this);
        getDriver().findElement(By.xpath("//INPUT[@id='sellerTable:"+id+":goToDetailsId']")).click();
        return sendMessagePO;
    }
}
