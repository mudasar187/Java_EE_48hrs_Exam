package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp() {

        clickAndWait("linkToSignupId");

        SignUpPO po = new SignUpPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public IndexPO doLogout() {

        clickAndWait("linktoLogoutId");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public LoginPO toLogin() {
        clickAndWait("linkToLoginId");

        LoginPO po = new LoginPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public InboxPO toInbox() {

        clickAndWait("linkToInboxId");

        InboxPO po = new InboxPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public IndexPO toHomePage() {

        clickAndWait("linkToHomePageId");

        IndexPO po = new IndexPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public int getNumberOfDisplayedRows(String table) {
        int rows = 0;
        rows = driver.findElements(
                By.xpath("//table[@id='" + table + "']//tbody//tr[string-length(text()) > 0]")).size();

        return rows;
    }

    public int getNumberOfDisplayedColumns(String table) {
        int columns = 0;
        columns = driver.findElements(
                By.xpath("//table[@id='" + table + "']//tbody//tr[1]//td")).size();
        return columns;
    }


    public boolean isLoggedIn() {

        return getDriver().findElements(By.id("linktoLogoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }
}
