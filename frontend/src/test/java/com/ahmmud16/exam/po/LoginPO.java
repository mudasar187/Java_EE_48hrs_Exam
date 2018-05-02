package com.ahmmud16.exam.po;

public class LoginPO extends LayoutPO{

    public LoginPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Login");
    }

    public IndexPO enterCredentials(String username, String password){

        setText("username", username);
        setText("password", password);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }
}
