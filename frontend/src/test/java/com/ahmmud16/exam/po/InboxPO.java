package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class InboxPO extends LayoutPO {


    public InboxPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public InboxPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Inbox");
    }

    public String getTextFromTable(String text) {
        return getDriver().findElement(By.xpath("//LABEL[text()='"+text+"']")).getText();
    }

    public SendMessagePO clickReplyButton(String id) {
        SendMessagePO sendMessagePO = new SendMessagePO(this);
        getDriver().findElement(By.xpath("//INPUT[@id='receivedMessagesTable:"+id+":sendMessageId']")).click();
        return sendMessagePO;
    }
}
