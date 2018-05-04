package com.ahmmud16.exam.po;

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
}
