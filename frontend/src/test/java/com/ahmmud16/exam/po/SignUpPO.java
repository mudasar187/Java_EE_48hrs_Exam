package com.ahmmud16.exam.po;

import org.openqa.selenium.By;

public class SignUpPO extends LayoutPO{

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Signup");
    }

    public IndexPO createUser(String username, String firstname, String lastname, String password, String retypePassword){

        setText("username", username);
        setText("firstname", firstname);
        setText("lastname", lastname);
        setText("password", password);
        setText("retypepassword", retypePassword);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }

    public IndexPO createUserAsAdmin(String username, String firstname, String lastname, String password, String retypePassword){

        setText("username", username);
        setText("firstname", firstname);
        setText("lastname", lastname);
        setText("password", password);
        driver.findElement(By.id("isAdmin")).click();
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }
}
