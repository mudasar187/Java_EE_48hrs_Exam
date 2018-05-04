package com.ahmmud16.exam.po;

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
}
