package com.ahmmud16.exam.po;

import org.openqa.selenium.By;

public class SignUpPO extends LayoutPO{

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign Up");
    }

    public IndexPO createUser(String userName, String password, String retypePassword){

        setText("username", userName);
        setText("password", password);
        setText("retypepassword", retypePassword);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }

    public IndexPO createUserAsAdmin(String userName, String password, String retypePassword){

        setText("username", userName);
        setText("password", password);
        setText("retypepassword", retypePassword);
        driver.findElement(By.id("isAdmin")).click();
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }
}
