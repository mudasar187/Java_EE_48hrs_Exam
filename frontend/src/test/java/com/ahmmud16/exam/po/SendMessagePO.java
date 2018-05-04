package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SendMessagePO extends LayoutPO {


    public SendMessagePO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public SendMessagePO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Send message");
    }


    public void createMessage(String text) {
        setText("sendMessageForm:bookDescription", text);
        clickAndWait("sendMessageForm:sendMessageId");
    }
}
