package com.ahmmud16.exam.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertTrue;

public abstract class LayoutPO extends PageObject {

    public LayoutPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public LayoutPO(PageObject other) {
        super(other);
    }

    public SignUpPO toSignUp(){

        clickAndWait("linkToSignupId");

        SignUpPO po = new SignUpPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public IndexPO doLogout(){

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





    public boolean isLoggedIn(){

        return getDriver().findElements(By.id("linktoLogoutId")).size() > 0 &&
                getDriver().findElements((By.id("linkToSignupId"))).isEmpty();
    }
}
