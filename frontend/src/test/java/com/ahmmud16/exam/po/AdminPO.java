package com.ahmmud16.exam.po;

public class AdminPO extends LayoutPO {

    public AdminPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Admin");
    }
}
